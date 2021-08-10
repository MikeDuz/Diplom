package skillbox.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentByPost {

    int id;
    long timestamp;
    String text;
    CommentUser user;
}
