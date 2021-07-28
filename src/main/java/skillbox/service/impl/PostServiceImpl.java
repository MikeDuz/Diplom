package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.Mode;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.SinglePostDto;
import skillbox.entity.Post;
import skillbox.entity.User;
import skillbox.entity.enums.ModerationStatus;
import skillbox.mapping.PostMapping;
import skillbox.repository.*;
import skillbox.service.PostService;
import skillbox.util.PostPublic;
import skillbox.util.SetLimit;
import skillbox.util.SetPageNumber;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static skillbox.dto.Mode.recent;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRep;
    private final PostVotesRepository postVotes;
    private final PostCommentsRepository postComment;
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;
    private final UserRepository userRep;

    @Override
    @Transactional
    public PostDto getPosts(int offset, int limit, Mode mode) {
        PostDto postDTO = new PostDto();
        int postCount = (int) postRep.count();
        postDTO.setCount(postCount);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, postCount);
        if (postCount == 0) {
            return postDTO;
        }
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> posts = postRep.findAll(LocalDateTime.now(ZoneOffset.UTC), paging);
        return PostMapping.postMapping(postDTO, posts, postVotes, postComment, mode);
    }

    @Override
    @Transactional
    public PostDto searchPost(int offset, int limit, String query) {
        PostDto postDTO = new PostDto();
        Pattern space = Pattern.compile("\\s+");
        Matcher spaceMatch = space.matcher(query);
        if (query.equals("") || spaceMatch.matches()) {
            postDTO.setCount(0);
            return postDTO;
        }
        int count = postRep.countAllByTitleContainsAndTextContains(query);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> queryPosts = postRep.findAllByTextContainsOrTitleContains(query, paging);
        if (queryPosts.getSize() == 0) {
            postDTO.setCount(0);
            return postDTO;
        }
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, queryPosts, postVotes, postComment, recent);
    }

    @Override
    @Transactional
    public PostDto searchPostByTag(int offset, int limit, String tag) {
        int count = tag2PostRepository.countAllByTagIdContains(tag);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> postByTag = tag2PostRepository.findPostByTag(tag, paging);
        PostDto postDTO = new PostDto();
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, postByTag, postVotes, postComment, recent);
    }

    @Override
    @Transactional
    public SinglePostDto searchPostById(int postId) {
        Post post = postRep.getOne(postId);
        SinglePostDto postDTO = new SinglePostDto();
        if (!PostPublic.postPublic(post)) {
            return postDTO;
        }
        postRep.incrViewCount(postId);
        return PostMapping.createSinglePost(post, postVotes, postComment, tag2PostRepository);
    }

    @Override
    @Transactional
    public PostDto searchPostByDate(int offset, int limit, String strDate) throws ParseException {
        PostDto postDTO = new PostDto();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(strDate);
        int count = postRep.countAllByDate(date);
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> page = postRep.findAllByTime(date, paging);
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, page, postVotes, postComment, recent);
    }

    @Override
    public PostDto searchMyPosts(int offset, int limit, String status, Principal principal) {
        String email = principal.getName();
        PostDto posts = new PostDto();
        switch (status) {
            case ("inactive"):
                myInactivePosts(posts, offset, limit, email);
                break;
            case ("pending"):
                myPosts(posts, offset, limit, ModerationStatus.NEW, email);
                break;
            case ("declined"):
                myPosts(posts, offset, limit, ModerationStatus.DECLINED, email);
                break;
            case ("published"):
                myPosts(posts, offset, limit, ModerationStatus.ACCEPTED, email);
                break;
        }
        return posts;
    }

    private PostDto myPosts(PostDto posts, int offset, int limit, ModerationStatus modStatus, String email) {
        boolean isActive = true;
        int count = postRep.countAllByModerationStatusAndActive(email, isActive, modStatus);
        if(count == 0) {
            posts.setCount(count);
            posts.setPosts(List.of());
            return posts;
        }
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> postPage = postRep.findMyPosts(email, isActive, modStatus, paging);
        return PostMapping.postMapping(posts, postPage, postVotes, postComment, recent);
    }

    private PostDto myInactivePosts(PostDto posts, int offset, int limit, String email) {
        boolean isActive = false;
        int count = postRep.countMyPostByActive(isActive, email);
        if(count == 0) {
            posts.setCount(count);
            posts.setPosts(List.of());
            return posts;
        }
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        Page<Post> inactivePosts = postRep.findMyPostInactive(isActive, email, paging);
        return PostMapping.postMapping(posts, inactivePosts, postVotes, postComment, recent);
    }

}
