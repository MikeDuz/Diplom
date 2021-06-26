package skillbox.mapping;


import lombok.RequiredArgsConstructor;
import skillbox.dto.post.PostDTO;
import skillbox.dto.post.PostUser;
import skillbox.dto.post.PostsInclude;
import skillbox.entity.Post;
import skillbox.repository.PostCommentsRepository;
import skillbox.repository.PostVotesRepository;
import skillbox.util.DateConvertor;
import skillbox.dto.Mode;
import skillbox.util.PostPublic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static skillbox.dto.Mode.*;

@RequiredArgsConstructor
public class PostMapping {


    public static PostDTO postMapping(PostDTO postDTO,
                                      List<Post> posts,
                                      PostVotesRepository postVotes,
                                      PostCommentsRepository postComment,
                                      Mode param) {
        List<PostsInclude> postInclude = new ArrayList<>();
        for (Post a : posts) {
            if(PostPublic.postPublic(a)) {
                PostsInclude post = new PostsInclude();
                post.setId(a.getId());
                post.setTimeStamp(DateConvertor.getTimestamp(a.getTime()));
                post.setTitle(a.getTitle());
                post.setViewCount(a.getViewCount());
                post.setLikeCount(postVotes.findAllLike(1, a.getId()));
                post.setDislikeCount(postVotes.findAllLike(-1, a.getId()));
                post.setCommentCount(postComment.findAllById(Collections.singleton(a.getId())).size());
                post.setUser(postUserSet(a));
                post.setAnnounce(createAnnounce(a.getText()));
                postInclude.add(post);
            }
        }
        sortArray(postInclude, param);
        postDTO.setPosts(postInclude);
        return postDTO;
    }

    private static PostUser postUserSet(Post a) {
        PostUser user = new PostUser();
        user.setId(a.getUserId().getId());
        user.setName(a.getUserId().getName());
        return user;
    }

    private static String createAnnounce(String postText) {
        if (postText.length() <= 150) {
            return postText;
        }
        Pattern pat = Pattern.compile("^(.{150}\\w*)");
        Matcher match = pat.matcher(postText);
        if (match.find()) {
            return match.group(1) + "...";
        }
        return "error";
    }

    private static void sortArray(List<PostsInclude> postInclude, Mode param) {
        if (param.equals(recent)) {
            postInclude.sort(Comparator.comparing(PostsInclude::getTimeStamp).reversed());
        }
        if (param.equals(popular)) {
            postInclude.sort(Comparator.comparing(PostsInclude::getCommentCount).reversed());
        }
        if (param.equals(best)) {
            postInclude.sort(Comparator.comparing(PostsInclude::getLikeCount).reversed());
        }
        if (param.equals(early)) {
            postInclude.sort(Comparator.comparing(PostsInclude::getTimeStamp));
        }
    }

}
