package skillbox.mapping;

import skillbox.dto.tag.TagDTO;
import skillbox.dto.tag.TagContain;
import skillbox.repository.TagPostCount;
import skillbox.repository.TagRepository;


import java.util.List;

public class TagMapping {

    public static TagDTO tagMapping(List<TagPostCount> tagCountList, double normK, double count, TagRepository tagRepository) {
        TagContain tagContain = new TagContain();
        TagDTO tagDTO = new TagDTO();
        tagCountList.forEach( a -> {
            tagContain.setName(tagRepository.findById(a.getTagId()).get().getName());
            tagContain.setWeight((a.getPostCount()/ count) * normK);
            tagDTO.addTag(tagContain);
        });
        return tagDTO;
    }

}
