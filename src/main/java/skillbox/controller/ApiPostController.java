package skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.dto.post.PostDTO;
import skillbox.dto.Mode;
import skillbox.dto.post.SinglePostDTO;
import skillbox.service.impl.PostServiceImpl;
import skillbox.service.impl.TagService;

import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Log4j2
public class ApiPostController {

    private final PostServiceImpl postService;
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

    @GetMapping("/{id}")
    public SinglePostDTO searchPostById(@PathVariable("id") int postId) {
        return postService.searchPostById(postId);
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostDTO> searchPostByDate(@RequestParam(required = false, defaultValue = "0") int offset,
                                                    @RequestParam(required = false, defaultValue = "10") int limit,
                                                    @RequestParam(required = true) String date) throws ParseException {
        return new ResponseEntity<>(postService.searchPostByDate(offset, limit, date), HttpStatus.OK);

    }
}
