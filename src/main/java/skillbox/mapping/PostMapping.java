package skillbox.mapping;


import lombok.RequiredArgsConstructor;
import skillbox.dto.post.*;
import skillbox.entity.Post;
import skillbox.entity.PostComment;
import skillbox.entity.Tag;
import skillbox.entity.User;
import skillbox.entity.enums.ModerationStatus;
import skillbox.entity.projection.PostProjection;
import skillbox.repository.PostVotesRepository;
import skillbox.util.DateConvertor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class PostMapping {


    public static PostDto postMapping(PostDto postDTO,
                                      List<PostProjection> posts) {
        List<PostInclude> includes = posts.stream().map(a -> createPostInclude(a))
                .filter(Objects::nonNull).collect(Collectors.toList());
        postDTO.setPosts(includes);
        return postDTO;
    }

    public static Post insertPostMapper(LocalDateTime time, PostRequest postRequest, User user, Post post, ModerationStatus modStatus) {
        post.setActive((postRequest.getActive() == 1) ? true : false);
        post.setModerationStatus(modStatus);
        post.setUserId(user);
        post.setTime(time);
        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());
        post.setViewCount(0);
        return post;
    }

    private static PostUser postUserSet(User user) {
        PostUser postUser = new PostUser();
        postUser.setId(user.getId());
        postUser.setName(user.getName());
        return postUser;
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


    public static PostInclude createPostInclude(PostProjection post) {
        PostInclude postInclude = PostInclude.builder()
                .id(post.getId())
                .timeStamp(DateConvertor.getTimestamp(post.getTime()))
                .title(post.getTitle())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .commentCount(post.getCommentCount())
                .user(postUserSet(post.getUser()))
                .announce(createAnnounce(post.getText())).build();
        return postInclude;
    }

    public static SinglePostDto createSinglePost(Post post,
                                                 PostVotesRepository postVotes) {
        SinglePostDto singlePost = SinglePostDto.builder()
        .id(post.getId())
        .timestamp(DateConvertor.getTimestamp(post.getTime()))
        .isActive(post.isActive())
        .user(postUserSet(post.getUserId()))
        .title(post.getTitle())
        .text(post.getText())
        .likeCount(postVotes.findAllLike(1, post.getId()))
        .dislikeCount(postVotes.findAllLike(-1, post.getId()))
        .viewCount(post.getViewCount())
        .comments(getCommentByPost(post))
        .tags(getTagByPost(post))
        .build();
        return singlePost;
    }

    private static Set<CommentByPost> getCommentByPost(Post post) {
        Set<PostComment> postComments = post.getComments();
        Set<CommentByPost> commentSet = postComments.stream().map(c ->
            CommentByPost.builder()
                    .id(c.getId())
                    .timestamp(DateConvertor.getTimestamp(c.getTime()))
                    .text(c.getText())
                    .user(getCommentUser(c.getUserId()))
                    .build()
        ).collect(Collectors.toSet());
        return commentSet;
    }

    private static CommentUser getCommentUser(User user) {
        CommentUser commentUser = new CommentUser();
        commentUser.setId(user.getId());
        commentUser.setName(user.getName());
        commentUser.setPhoto(user.getPhoto());
        return commentUser;
    }

    private static Set<String> getTagByPost(Post post) {
        Set<Tag> tagByPost = post.getTags();
        Set<String> tagSet = tagByPost.stream().map(a -> a.getName())
        .collect(Collectors.toSet());
        return tagSet;
    }

}


