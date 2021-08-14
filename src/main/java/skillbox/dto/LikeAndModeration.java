package skillbox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeAndModeration {

    @JsonProperty("post_id")
    private int postId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String decision;
}
