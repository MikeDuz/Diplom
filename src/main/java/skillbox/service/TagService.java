package skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.dto.tag.TagDTO;
import skillbox.dto.tag.TagContain;
import skillbox.entity.Tag2Post;
import skillbox.mapping.TagMapping;
import skillbox.repository.PostRepository;
import skillbox.repository.TagRepository;
import skillbox.util.TagCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagService {

    private final PostRepository posts;
    private final TagRepository tagRepository;

    public TagDTO getTag(String query) {
        TagContain tagContain = new TagContain();
        double count = (double) posts.count();
        Map<Integer, Integer> tagCounts = tagRepository.findCountTag();
        double normK = 1/ count;
        if(query.equals("all")) {
            return TagMapping.tagMapping(tagCounts, normK, count);
        }
        return null;
    }
}
