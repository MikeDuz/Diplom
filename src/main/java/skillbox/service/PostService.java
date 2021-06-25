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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static skillbox.util.Mode.recent;

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
        limit = setLimit(offset, limit, postCount);
        if(postCount == 0) {
            return postDTO;
        }
        Pageable p = PageRequest.of(offset, limit);
        List<Posts> posts = postRepository.findAll(p).getContent();
        return PostMapping.postMapping(postDTO, posts, postVotes, postComment, mode);

    }

    public PostDTO searchPost(int offset, int limit, String query) {
        PostDTO postDTO = new PostDTO();
        Pattern space = Pattern.compile("\\s+");
        Matcher spaceMatch = space.matcher(query);
        if(query.equals("") || spaceMatch.matches()) {
            postDTO.setCount(0);
            return postDTO;
        }
        List<Posts> allPost = postRepository.findAll();
        if(allPost.size() == 0) {
            postDTO.setCount(0);
            return postDTO;
        }
        Pattern searchQuery = Pattern.compile(".*" + query + ".*");
        List<Posts> searchPost = new ArrayList<>();
        for(Posts p: allPost) {
            Matcher title = searchQuery.matcher(p.getTitle());
            Matcher text = searchQuery.matcher(p.getText());
            if(title.find() || text.find()) {
                searchPost.add(p);
            }
            postDTO.setCount(searchPost.size());
        }
        postDTO.setCount(searchPost.size());
        limit = setLimit(offset, limit, searchPost.size());
        List<Posts> pageList = searchPost.subList(offset, limit);
        return PostMapping.postMapping(postDTO, pageList, postVotes, postComment, recent);
    }

    private int setLimit(int offset, int limit, int count) {
        if((count - offset ) <= limit) {
            return count - offset;
        }
        return limit;
    }


}
