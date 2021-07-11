package skillbox.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SinglePostDTO {

    int id;
    long timestamp;
    @JsonProperty("active")
    boolean isActive;
    PostUser user;
    String title;
    String text;
    int likeCount;
    int dislikeCount;
    int viewCount;
    List<CommentByPost> comments;
    List<String> tagByPost;

}
