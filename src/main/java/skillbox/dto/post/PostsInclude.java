package skillbox.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostsInclude {

    int id;
    @JsonProperty("timestamp")
    LocalDateTime timeStamp;
    PostUser user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;

}
