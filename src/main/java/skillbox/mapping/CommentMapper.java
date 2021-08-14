package skillbox.mapping;

import skillbox.dto.comment.CommentDto;
import skillbox.entity.Post;
import skillbox.entity.PostComment;
import skillbox.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CommentMapper {

    public static PostComment commentMapper(CommentDto commentDto,
                                            Post post,
                                            PostComment parentComment,
                                            User user) {
        PostComment postComment = new PostComment();
        postComment.setParentId(parentComment);
        postComment.setPost(post);
        postComment.setUserId(user);
        postComment.setTime(LocalDateTime.now(ZoneOffset.UTC));
        postComment.setText(commentDto.getText());
        return postComment;
    }
}
