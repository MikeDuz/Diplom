package skillbox.mapping;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import skillbox.dto.post.*;
import skillbox.entity.Post;
import skillbox.entity.PostComments;
import skillbox.entity.Tag2Post;
import skillbox.entity.User;
import skillbox.entity.enums.ModerationStatus;
import skillbox.entity.projection.PostProjection;
import skillbox.repository.PostCommentsRepository;
import skillbox.repository.PostVotesRepository;
import skillbox.repository.Tag2PostRepository;
import skillbox.repository.UserRepository;
import skillbox.util.DateConvertor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class PostMapping {

    private final static UserRepository userRep = null;


    public static PostDto postMapping(PostDto postDTO,
                                      List<PostProjection> posts) {
        List<PostInclude> includes = posts.stream().map(a -> createPostInclude(a))
                .filter(Objects::nonNull).collect(Collectors.toList());
        postDTO.setPosts(includes);
        return postDTO;
    }

    public static Post insertUserMapper(LocalDateTime time, PostRequest postRequest, User user) {
        Post post = new Post();
        post.setActive((postRequest.getActive() == 1) ?  true : false);
        post.setModerationStatus(ModerationStatus.NEW);
        post.setUserId(user);
        post.setTime(time);
        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());
        post.setViewCount(0);
        return post;
    }

    private static PostUser postUserSet(User user) {
        PostUser postUser = new PostUser();
        user.setId(user.getId());
        user.setName(user.getName());
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
                                                 PostVotesRepository postVotes,
                                                 PostCommentsRepository postComment,
                                                 Tag2PostRepository tag2PostRepository) {
        SinglePostDto singlePost = new SinglePostDto();
        singlePost.setId(post.getId());
        singlePost.setTimestamp(DateConvertor.getTimestamp(post.getTime()));
        singlePost.setActive(post.isActive());
        singlePost.setUser(postUserSet(post.getUserId()));
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


