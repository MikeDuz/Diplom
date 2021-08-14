package skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skillbox.dto.WrapperResponse;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.SinglePostDto;
import skillbox.dto.LikeAndModeration;
import skillbox.service.PostService;
import skillbox.service.PostVoteService;

import java.security.Principal;
import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Log4j2
public class ApiPostController {

    private final PostService postService;
    private final PostVoteService postVoteService;

    @GetMapping()
    public ResponseEntity<PostDto> getPost(@RequestParam(required = false, defaultValue = "0") int offset,
                                           @RequestParam(required = false, defaultValue = "10") int limit,
                                           @RequestParam(defaultValue = "recent") String mode) {
        return new ResponseEntity<>(postService.getPosts(offset, limit, mode), HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<PostDto> searchPost(@RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "10") int limit,
                                              @RequestParam(defaultValue = "") String query) {
        return new ResponseEntity<>(postService.searchPost(offset, limit, query), HttpStatus.OK);
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostDto> searchPostByTag(@RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "10") int limit,
                                                   @RequestParam(defaultValue = "") String tag) {
        return new ResponseEntity<>(postService.searchPostByTag(offset, limit, tag), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinglePostDto> searchPostById(@PathVariable("id") int postId) {
        return new ResponseEntity<>(postService.searchPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostDto> searchPostByDate(@RequestParam(required = false, defaultValue = "0") int offset,
                                                    @RequestParam(required = false, defaultValue = "10") int limit,
                                                    @RequestParam(required = true) String date) throws ParseException {
        return new ResponseEntity<>(postService.searchPostByDate(offset, limit, date), HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<PostDto> searchMyPost(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(defaultValue = "published") String status,
            Principal principal) {
        return new ResponseEntity<>(postService.searchMyPosts(offset, limit, status, principal), HttpStatus.OK);
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostDto> searchPostsForModeration(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(defaultValue = "new") String status,
            Principal principal) {
        return new ResponseEntity<>(postService.searchModeratedPost(offset, limit, status, principal), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<WrapperResponse> publicPost(@RequestBody skillbox.dto.post.PostRequest postRequest,
                                                      Principal principal) {
        return new ResponseEntity<>(postService.insertPost(postRequest, principal), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<WrapperResponse> editPost(
            @PathVariable("id") int postId,
            @RequestBody skillbox.dto.post.PostRequest postRequest,
            Principal principal) {
        return new ResponseEntity<>(postService.changePost(postId, postRequest, principal), HttpStatus.OK);
    }

    @PostMapping("/like")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<WrapperResponse> likePost(@RequestBody LikeAndModeration postId,
                                                    Principal principal) {
        int param = 1;
        return new ResponseEntity<>(postVoteService.addLike2Post(principal, postId, param), HttpStatus.OK);
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<WrapperResponse> dislikePost(@RequestBody LikeAndModeration postId,
                                                       Principal principal) {
        int param = - 1;
        return new ResponseEntity<>(postVoteService.addLike2Post(principal, postId, param), HttpStatus.OK);
    }

}
