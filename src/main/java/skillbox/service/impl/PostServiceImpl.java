package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.Mode;
import skillbox.dto.post.PostDTO;
import skillbox.dto.post.SinglePostDTO;
import skillbox.entity.Post;
import skillbox.mapping.PostMapping;
import skillbox.repository.*;
import skillbox.service.PostService;
import skillbox.util.PostPublic;
import skillbox.util.SetLimit;
import skillbox.util.SetPageNumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static skillbox.dto.Mode.recent;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotes;
    private final PostCommentsRepository postComment;
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;
    private final UserRepository userRepository;

    @Override
    public PostDTO getPosts(int offset, int limit, Mode mode) {
        PostDTO postDTO = new PostDTO();
        int postCount = (int) postRepository.count();
        postDTO.setCount(postCount);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, postCount);
        if (postCount == 0) {
            return postDTO;
        }
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> posts = postRepository.findAll(LocalDateTime.now(), paging);
        return PostMapping.postMapping(postDTO, posts, postVotes, postComment, mode, true);

    }

    @Override
    public PostDTO searchPost(int offset, int limit, String query) {
        PostDTO postDTO = new PostDTO();
        Pattern space = Pattern.compile("\\s+");
        Matcher spaceMatch = space.matcher(query);
        if (query.equals("") || spaceMatch.matches()) {
            postDTO.setCount(0);
            return postDTO;
        }
        int count = postRepository.countAllByTitleContainsAndTextContains(query);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> queryPosts = postRepository.findAllByTextContainsOrTitleContains(query, paging);
        if (queryPosts.getSize() == 0) {
            postDTO.setCount(0);
            return postDTO;
        }
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, queryPosts, postVotes, postComment, recent, true);
    }

    @Override
    public PostDTO searchPostByTag(int offset, int limit, String tag) {
        int count = tag2PostRepository.countAllByTagIdContains(tag);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> postByTag = tag2PostRepository.findPostByTag(tag, paging);
        PostDTO postDTO = new PostDTO();
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, postByTag, postVotes, postComment, recent, true);
    }

    @Override
    @Transactional
    public SinglePostDTO searchPostById(int postId) {
        Post post = postRepository.getOne(postId);
        SinglePostDTO postDTO = new SinglePostDTO();
        if(post == null || !PostPublic.postPublic(post)) {
            return postDTO;
        }
        postRepository.incrViewCount(postId);
        return PostMapping.createSinglePost(post, postVotes, postComment, tag2PostRepository);
    }

    @Override
    @Transactional
    public PostDTO searchPostByDate(int offset, int limit, String strDate) throws ParseException {
        PostDTO postDTO = new PostDTO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(strDate);
        int count = postRepository.countAllByDate(date);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> page = postRepository.findAllByTime(date, paging);
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, page, postVotes, postComment, recent, true);
    }

}
