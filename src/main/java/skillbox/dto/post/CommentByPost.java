package skillbox.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentByPost {

    int id;
    long timestamp;
    String text;
    CommentUser user;
}
