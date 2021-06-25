package skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skillbox.dto.post.PostDTO;
import skillbox.util.Mode;
import skillbox.service.PostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Log4j2
public class ApiPostController {

    private final PostService postService;

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

}
