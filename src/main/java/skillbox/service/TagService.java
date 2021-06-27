package skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.dto.tag.TagDTO;
import skillbox.entity.Tags;
import skillbox.mapping.TagMapping;
import skillbox.repository.PostRepository;
import skillbox.repository.Tag2PostRepository;
import skillbox.repository.TagPostCount;
import skillbox.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class TagService {

    private final PostRepository posts;
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;

    public TagDTO getTag(String query) {
        double count = (double) posts.count();
        List<TagPostCount> tagCounts = tag2PostRepository.getTagPostCounts();
        double normK = count / tagCounts.get(0).getPostCount();
        if (query.equals("all")) {
            return TagMapping.tagMapping(tagCounts, normK, count, tagRepository);
        }
        List<Tags> tagList = tagRepository.findAll();
        tagCounts.forEach(a -> tagSearch(tagList, query).stream().
                filter(b -> a.getTagId() != b.getId()).map(b -> a).
                forEachOrdered(tagList::remove));

        return TagMapping.tagMapping(tagCounts, normK, count, tagRepository);
    }

    private List<Tags> tagSearch(List<Tags> tagList, String query) {
        Pattern pat = Pattern.compile(query + ".*");
        List<Tags> queryTags = new ArrayList<>();
        tagList.stream().forEach(a -> {
            Matcher match = pat.matcher(a.getName());
            if (match.find()) {
                queryTags.add(a);
            }
        });
        return queryTags;
    }

}
