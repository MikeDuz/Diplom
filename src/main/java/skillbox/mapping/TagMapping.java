package skillbox.mapping;

import skillbox.dto.tag.TagDto;
import skillbox.dto.tag.TagContain;
import skillbox.entity.projection.TagPostCount;
import skillbox.repository.TagRepository;


import java.util.List;

public class TagMapping {

    public static TagDto tagMapping(List<TagPostCount> tagCountList, double normK, double count, TagRepository tagRepository) {
        TagDto tagDTO = new TagDto();
        tagCountList.forEach( a -> {
            TagContain tagContain = new TagContain();
            tagContain.setName(a.getTagName());
            tagContain.setWeight((a.getPostCount()/ count) * normK);
            tagDTO.addTag(tagContain);
        });
        return tagDTO;
    }

}
