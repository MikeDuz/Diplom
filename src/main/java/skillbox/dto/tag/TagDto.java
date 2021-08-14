package skillbox.dto.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class TagDto {

    List<TagContain> tags = new ArrayList<>();

    public void addTag(TagContain tagContain) {
        tags.add(tagContain);
    }

}
