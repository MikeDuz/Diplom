package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.dto.post.PostRequest;
import skillbox.dto.tag.TagDTO;
import skillbox.entity.Post;
import skillbox.entity.Tag;
import skillbox.mapping.TagMapping;
import skillbox.repository.PostRepository;
import skillbox.repository.Tag2PostRepository;
import skillbox.entity.projection.TagPostCount;
import skillbox.repository.TagRepository;
import skillbox.service.TagService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final PostRepository posts;
    private final TagRepository tagRep;
    private final Tag2PostRepository tag2PostRepository;

    @Override
    public TagDTO getTag(String query) {
        double count = (double) posts.count();
        List<TagPostCount> tagCounts = tag2PostRepository.getTagPostCounts();
        double normK = count / tagCounts.get(0).getPostCount();
        if (query.equals("all")) {
            return TagMapping.tagMapping(tagCounts, normK, count, tagRep);
        }
        List<Tag> tagList = tagRep.findAll();
        tagCounts.forEach(a -> tagSearch(tagList, query).stream().
                filter(b -> a.getTagId() != b.getId()).map(b -> a).
                forEachOrdered(tagList::remove));

        return TagMapping.tagMapping(tagCounts, normK, count, tagRep);
    }

    public boolean tagDuplicateSearch(String tag) {
        return tagRep.existsByName(tag);
    }

    private List<Tag> tagSearch(List<Tag> tagList, String query) {
        Pattern pat = Pattern.compile(query + ".*");
        List<Tag> queryTags = new ArrayList<>();
        tagList.stream().forEach(a -> {
            Matcher match = pat.matcher(a.getName());
            if (match.find()) {
                queryTags.add(a);
            }
        });
        return queryTags;
    }


}
