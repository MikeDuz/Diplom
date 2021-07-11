package skillbox.mapping;


import lombok.RequiredArgsConstructor;
import skillbox.dto.post.*;
import skillbox.entity.Post;
import skillbox.entity.PostComments;
import skillbox.entity.Tag2Post;
import skillbox.entity.User;
import skillbox.repository.PostCommentsRepository;
import skillbox.repository.PostVotesRepository;
import skillbox.repository.Tag2PostRepository;
import skillbox.repository.UserRepository;
import skillbox.util.DateConvertor;
import skillbox.dto.Mode;
import skillbox.util.PostPublic;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static skillbox.dto.Mode.*;

@RequiredArgsConstructor
public class PostMapping {


    public static PostDTO postMapping(PostDTO postDTO,
                                      List<Post> posts,
                                      PostVotesRepository postVotes,
                                      PostCommentsRepository postComment,
                                      Mode param,
                                      boolean isShort) {
        List<PostInclude> includes = new ArrayList<>();
        for (Post a : posts) {
            PostInclude postInclude = createPostInclude(a,
                    postVotes,
                    postComment,
                    isShort);
            if (postInclude != null) {
                includes.add(postInclude);
            }
        }
        sortArray(includes, param);
        postDTO.setPosts(includes);
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

    private static void sortArray(List<PostInclude> postInclude, Mode param) {
        if (param.equals(recent)) {
            postInclude.sort(Comparator.comparing(PostInclude::getTimeStamp).reversed());
        }
        if (param.equals(popular)) {
            postInclude.sort(Comparator.comparing(PostInclude::getCommentCount).reversed());
        }
        if (param.equals(best)) {
            postInclude.sort(Comparator.comparing(PostInclude::getLikeCount).reversed());
        }
        if (param.equals(early)) {
            postInclude.sort(Comparator.comparing(PostInclude::getTimeStamp));
        }
    }

    public static PostInclude createPostInclude(Post post,
                                                PostVotesRepository postVotes,
                                                PostCommentsRepository postComment,
                                                boolean isShort) {
        if (PostPublic.postPublic(post)) {
            PostInclude postInclude = new PostInclude();
            postInclude.setId(post.getId());
            postInclude.setTimeStamp(DateConvertor.getTimestamp(post.getTime()));
            postInclude.setTitle(post.getTitle());
            postInclude.setViewCount(post.getViewCount());
            postInclude.setLikeCount(postVotes.findAllLike(1, post.getId()));
            postInclude.setDislikeCount(postVotes.findAllLike(-1, post.getId()));
            postInclude.setCommentCount(postComment.findAllById(Collections.singleton(post.getId())).size());
            postInclude.setUser(postUserSet(post));
            postInclude.setAnnounce(createAnnounce(post.getText()));
            return postInclude;
        }
        return null;
    }

    public static SinglePostDTO createSinglePost(Post post,
                                                 PostVotesRepository postVotes,
                                                 PostCommentsRepository postComment,
                                                 Tag2PostRepository tag2PostRepository) {
        SinglePostDTO singlePost = new SinglePostDTO();
        singlePost.setId(post.getId());
        singlePost.setTimestamp(DateConvertor.getTimestamp(post.getTime()));
        singlePost.setActive(post.isActive());
        singlePost.setUser(postUserSet(post));
        singlePost.setTitle(post.getTitle());
        singlePost.setText(post.getText());
        singlePost.setLikeCount(postVotes.findAllLike(1, post.getId()));
        singlePost.setDislikeCount(postVotes.findAllLike(-1, post.getId()));
        singlePost.setViewCount(post.getViewCount());
        singlePost.setComments(getCommentByPost(post.getId(), postComment));
        singlePost.setTagByPost(getTagByPost(post.getId(), tag2PostRepository));

    return singlePost;
    }

    private static List<CommentByPost> getCommentByPost(int postId,
                                                       PostCommentsRepository postComment) {
        List<PostComments> postComments = postComment.findAllByPostId(postId);
        List<CommentByPost> commentList = new ArrayList<>();
        for (PostComments c : postComments) {
            CommentByPost commentByPost = new CommentByPost();
            commentByPost.setId(c.getId());
            commentByPost.setTimestamp(DateConvertor.getTimestamp(c.getTime()));
            commentByPost.setText(c.getText());
            commentByPost.setUser(getCommentUser(c.getUserId()));
            commentList.add(commentByPost);
        }
        return commentList;
    }

    private static CommentUser getCommentUser(User user) {
        CommentUser commentUser = new CommentUser();
        commentUser.setId(user.getId());
        commentUser.setName(user.getName());
        commentUser.setPhoto(user.getPhoto());
        return commentUser;
    }

    private static List<String> getTagByPost(int postId, Tag2PostRepository tag2PostRepository) {
        List<Tag2Post> tagByPost = tag2PostRepository.findTagByPost(postId);
        List<String> tagList = new ArrayList<>();
        tagByPost.forEach(a -> {
            tagList.add(a.getTagId().getName());
        });
        return tagList;
    }
}


