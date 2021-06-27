package skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.dto.Mode;
import skillbox.dto.post.PostDTO;
import skillbox.dto.post.SinglePostDTO;
import skillbox.entity.Post;
import skillbox.entity.Tag2Post;
import skillbox.mapping.PostMapping;
import skillbox.repository.*;
import skillbox.util.PostPublic;
import skillbox.util.SetLimit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static skillbox.dto.Mode.recent;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotes;
    private final PostCommentsRepository postComment;
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;
    private final UserRepository userRepository;

    public PostDTO getPosts(int offset, int limit, Mode mode) {
        PostDTO postDTO = new PostDTO();
        int postCount = (int) postRepository.count();
        postDTO.setCount(postCount);
        limit = SetLimit.setLimit(offset, limit, postCount);
        if (postCount == 0) {
            return postDTO;
        }
        List<Post> posts = postRepository.findAll(LocalDateTime.now());
        List<Post> postPage = posts.subList(offset, offset + limit);
        return PostMapping.postMapping(postDTO, postPage, postVotes, postComment, mode, true);

    }

    public PostDTO searchPost(int offset, int limit, String query) {
        PostDTO postDTO = new PostDTO();
        Pattern space = Pattern.compile("\\s+");
        Matcher spaceMatch = space.matcher(query);
        if (query.equals("") || spaceMatch.matches()) {
            postDTO.setCount(0);
            return postDTO;
        }
        List<Post> allPost = postRepository.findAll(LocalDateTime.now());
        if (allPost.size() == 0) {
            postDTO.setCount(0);
            return postDTO;
        }
        Pattern searchQuery = Pattern.compile(".*" + query + ".*");
        List<Post> searchPost = new ArrayList<>();
        allPost.forEach(p -> {
            Matcher title = searchQuery.matcher(p.getTitle());
            Matcher text = searchQuery.matcher(p.getText());
            if (title.find() || text.find()) {
                searchPost.add(p);
            }
            postDTO.setCount(searchPost.size());
        });
        postDTO.setCount(searchPost.size());
        limit = SetLimit.setLimit(offset, limit, searchPost.size());
        List<Post> pageList = searchPost.subList(offset, offset + limit);
        return PostMapping.postMapping(postDTO, pageList, postVotes, postComment, recent, true);
    }

    public PostDTO searchPostByTag(int offset, int limit, String tag) {
        List<Tag2Post> tag2PostList = tag2PostRepository.getTag2PostByTagId(tag);
        List<Post> postByTag = new ArrayList<>();
        tag2PostList.forEach(a -> {
            Post post = postRepository.getOne(a.getPostId().getId());
            postByTag.add(post);
        });
        limit = SetLimit.setLimit(offset, limit, postByTag.size());
        List<Post> pageList = postByTag.subList(offset, limit);
        PostDTO postDTO = new PostDTO();
        postDTO.setCount(postByTag.size());
        return PostMapping.postMapping(postDTO, pageList, postVotes, postComment, recent, true);
    }

    public SinglePostDTO searchPostById(int postId) {
        Post post = postRepository.getOne(postId);
        SinglePostDTO postDTO = new SinglePostDTO();
        if(post == null || !PostPublic.postPublic(post)) {
            return postDTO;
        }
        //postRepository.incrViewCount(postId);
        return PostMapping.createSinglePost(post, postVotes, postComment, tag2PostRepository);
    }

}
