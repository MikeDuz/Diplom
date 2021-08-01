package skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skillbox.dto.WrapperResponse;
import skillbox.dto.post.PostRequest;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.SinglePostDto;
import skillbox.service.impl.PostServiceImpl;
import skillbox.service.impl.TagService;

import java.security.Principal;
import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Log4j2
public class ApiPostController {

    private final PostServiceImpl postService;
    private final TagService tagService;

    @GetMapping()
    public PostDto getPost(@RequestParam(required = false, defaultValue = "0") int offset,
                           @RequestParam(required = false, defaultValue = "10") int limit,
                           @RequestParam(defaultValue = "recent") String mode) {
        return postService.getPosts(offset, limit, mode);

    }

    @GetMapping("/search")
    public PostDto searchPost(@RequestParam(required = false, defaultValue = "0") int offset,
                              @RequestParam(required = false, defaultValue = "10") int limit,
                              @RequestParam(defaultValue = "") String query) {
        return postService.searchPost(offset, limit, query);
    }

    @GetMapping("/byTag")
    public PostDto searchPostByTag(@RequestParam(required = false, defaultValue = "0") int offset,
                                   @RequestParam(required = false, defaultValue = "10") int limit,
                                   @RequestParam(defaultValue = "") String tag) {
        return postService.searchPostByTag(offset, limit, tag);
    }

    @GetMapping("/{id}")
    public SinglePostDto searchPostById(@PathVariable("id") int postId) {
        return postService.searchPostById(postId);
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
    public ResponseEntity<WrapperResponse> publicPost(@RequestBody PostRequest postRequest,
                                                      Principal principal) {
        return new ResponseEntity<>(postService.insertPost(postRequest, principal), HttpStatus.OK);
    }
}
