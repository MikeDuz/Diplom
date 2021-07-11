package skillbox.util;

import skillbox.entity.Post;
import skillbox.entity.enums.ModerationStatus;

import java.time.LocalDateTime;

public class PostPublic {

    public static boolean postPublic(Post post) {
        if (post.isActive() &&
                post.getModerationStatus().equals(ModerationStatus.ACCEPTED) &&
                post.getTime().isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
