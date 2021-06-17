package skillbox.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDTO {

    int count;
    List<PostsInclude> posts;
}
