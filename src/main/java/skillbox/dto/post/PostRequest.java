package skillbox.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class PostRequest {

    Timestamp timestamp;
    int active;
    String title;
    Set<String> tags;
    String text;
}
