package skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import skillbox.dto.post.PostDTO;
import skillbox.entity.Posts;
import skillbox.util.Mode;
import skillbox.mapping.PostMapping;
import skillbox.repository.PostCommentsRepository;
import skillbox.repository.PostRepository;
import skillbox.repository.PostVotesRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotes;
    private final PostCommentsRepository postComment;

    public PostDTO getPosts(int offset, int limit, Mode mode) {
        PostDTO postDTO = new PostDTO();
        int postCount = (int) postRepository.count();
        postDTO.setCount(postCount);
        if(postCount < limit) {
            limit = postCount - offset;
        }
        if(postCount == 0) {
            return postDTO;
        }
        Pageable p = PageRequest.of(offset, limit);
        Page<Posts> posts = postRepository.findAll(p);
        return PostMapping.postMapping(postDTO, posts, postVotes, postComment);

    }
}
