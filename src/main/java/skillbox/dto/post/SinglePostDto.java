package skillbox.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Name;
import lombok.*;
import skillbox.entity.PostComment;
import skillbox.entity.Tag;

import java.util.List;
import java.util.Set;

@Builder
public class SinglePostDto {

    int id;
    @JsonProperty("timestamp")
    long timestamp;
    @JsonProperty("active")
    boolean isActive;
    @JsonProperty("user")
    PostUser user;
    @JsonProperty("title")
    String title;
    @JsonProperty("text")
    String text;
    @JsonProperty("likeCount")
    int likeCount;
    @JsonProperty("dislikeCount")
    int dislikeCount;
    @JsonProperty("viewCount")
    int viewCount;
    @JsonProperty("comments")
    Set<CommentByPost> comments;
    @JsonProperty("tags")
    Set<String> tags;

}
