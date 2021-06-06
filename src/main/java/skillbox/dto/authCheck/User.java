package skillbox.dto.authCheck;

import java.time.LocalDateTime;

public class User {

    int id;
    LocalDateTime timeStamp;
    AuthUser user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;
}
