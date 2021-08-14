package skillbox.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("post_id")
    private int postId;
    private String text;

}
