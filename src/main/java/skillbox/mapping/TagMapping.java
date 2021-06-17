package skillbox.mapping;

import skillbox.dto.tag.TagDTO;
import skillbox.dto.tag.TagContain;
import skillbox.util.TagCount;

import java.util.List;
import java.util.Map;

public class TagMapping {

    public static TagDTO tagMapping(Map<int, Double> tagCountList, double normK, double count) {
        TagContain tagContain = new TagContain();
        TagDTO tagDTO = new TagDTO();
        tagCountList.forEach((a, b) -> {
            tagContain.setName(a);
            tagContain.setWeight((b / count) * normK);
            tagDTO.getTags().add(tagContain);
        });
        return tagDTO;
    }

}
