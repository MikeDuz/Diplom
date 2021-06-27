package skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import skillbox.dto.post.PostDTO;
import skillbox.dto.Mode;
import skillbox.dto.post.SinglePostDTO;
import skillbox.service.PostService;
import skillbox.service.TagService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Log4j2
public class ApiPostController {

    private final PostService postService;
    private final TagService tagService;

    @GetMapping()
    public PostDTO getPost(@RequestParam(required = false, defaultValue = "0") int offset,
                           @RequestParam(required = false, defaultValue = "10") int limit,
                           @RequestParam(defaultValue = "recent") Mode mode) {
       return postService.getPosts(offset, limit, mode);

    }

    @GetMapping("/search")
    public PostDTO searchPost(@RequestParam(required = false, defaultValue = "0") int offset,
                             @RequestParam(required = false, defaultValue = "10") int limit,
                             @RequestParam(defaultValue = "") String query) {
        return postService.searchPost(offset, limit, query);
    }

    @GetMapping("/byTag")
    public PostDTO searchPostByTag(@RequestParam(required = false, defaultValue = "0") int offset,
                                   @RequestParam(required = false, defaultValue = "10") int limit,
                                   @RequestParam(defaultValue = "") String tag) {
        return postService.searchPostByTag(offset, limit, tag);
    }

    @GetMapping("/{ID}")
    public SinglePostDTO searchPostById(@PathVariable("ID") int postId) {
        if(postService.searchPostById(postId) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "post not found"
            );
        }
        return postService.searchPostById(postId);
    }
}
