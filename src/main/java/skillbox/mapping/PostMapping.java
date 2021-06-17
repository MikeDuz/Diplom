package skillbox.mapping;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import skillbox.dto.post.PostDTO;
import skillbox.dto.post.PostUser;
import skillbox.dto.post.PostsInclude;
import skillbox.entity.Posts;
import skillbox.repository.PostCommentsRepository;
import skillbox.repository.PostVotesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class PostMapping{


    public static PostDTO postMapping(PostDTO postDTO, Page<Posts> posts, PostVotesRepository postVotes, PostCommentsRepository postComment) {
        PostsInclude post = new PostsInclude();
        PostUser user = new PostUser();
        List<PostsInclude> postInclude = new ArrayList<>();
                posts.forEach(a -> {
                    post.setId(a.getId());
                    post.setTimeStamp(a.getTime());
                    post.setTitle(a.getTitle());
                    post.setViewCount(a.getViewCount());
                    post.setLikeCount(postVotes.findAllLike( 1, a.getId()));
                    post.setDislikeCount(postVotes.findAllLike( - 1, a.getId()));
                    post.setCommentCount(postComment.findAllById(Collections.singleton(a.getId())).size());
                    post.setUser(postUserSet(user, a));
                    post.setAnnounce(a.getText());
                    postInclude.add(post);
                }
        );
        postDTO.setPosts(postInclude);
        return postDTO;
    }

    private static PostUser postUserSet (PostUser user, Posts a){
        user.setId(a.getUserId().getId());
        user.setName(a.getUserId().getName());
        return user;
    }

}
