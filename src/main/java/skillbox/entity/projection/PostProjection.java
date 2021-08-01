package skillbox.entity.projection;

import skillbox.entity.User;

import java.time.LocalDateTime;

public interface PostProjection {
    int getId();
    LocalDateTime getTime();
    User getUser();
    String getTitle();
    String getText();
    int getLikeCount();
    int getDislikeCount();
    int getCommentCount();
    int getViewCount();

}
