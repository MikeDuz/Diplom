package skillbox.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDto {

    int count;
    List<PostInclude> posts;
}
