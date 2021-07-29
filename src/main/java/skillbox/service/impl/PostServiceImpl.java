package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.Mode;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.SinglePostDto;
import skillbox.entity.Post;
import skillbox.entity.enums.ModerationStatus;
import skillbox.mapping.MapModerationStatus;
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
        PostDto posts = new PostDto();
        int count = tag2PostRepository.countAllByTagIdContains(tag);
        if (count == 0) {
            return setCountZero(posts);
        }
        Pageable paging = getPageable(offset, limit, count);;
        Page<Post> postByTag = tag2PostRepository.findPostByTag(tag, paging);
        posts.setCount(count);
        return PostMapping.postMapping(posts, postByTag, postVotes, postComment, recent);
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
        PostDto posts = new PostDto();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(strDate);
        int count = postRep.countAllByDate(date);
        if (count == 0) {
            return setCountZero(posts);
        }
        Pageable paging = getPageable(offset, limit, count);
        Page<Post> page = postRep.findAllByTime(date, paging);
        posts.setCount(count);
        return PostMapping.postMapping(posts, page, postVotes, postComment, recent);
    }

    @Override
    public PostDto searchMyPosts(int offset, int limit, String status, Principal principal) {
        String email = principal.getName();
        PostDto posts = new PostDto();
        boolean flag = true;
        switch (status) {
            case ("inactive"):
                myInactivePosts(posts, offset, limit, email);
                break;
            case ("pending"):
                myPosts(posts, offset, limit, ModerationStatus.NEW, email, flag);
                break;
            case ("declined"):
                myPosts(posts, offset, limit, ModerationStatus.DECLINED, email, flag);
                break;
            case ("published"):
                myPosts(posts, offset, limit, ModerationStatus.ACCEPTED, email, flag);
                break;
        }
        return posts;
    }

    @Override
    public PostDto searchModeratedPost(int offset, int limit, String status, Principal principal) {
        ModerationStatus modStatus = MapModerationStatus.mapModStatus(status);
        PostDto posts = new PostDto();
        if(modStatus.equals(ModerationStatus.NEW)) {
            boolean isActive = true;
            int count = postRep.countPostsByIsActiveAndModerationStatus(isActive, modStatus);
            if (count == 0) {
                return setCountZero(posts);
            }
            posts.setCount(count);
            Pageable paging = getPageable(offset, limit, count);
            Page<Post> postMod = postRep.findAllByModerationStatus(modStatus, paging);
            return  PostMapping.postMapping(posts, postMod, postVotes, postComment, recent);
        }
        String email = principal.getName();
        boolean flag = false;
        return myPosts(posts, offset, limit, modStatus, email, flag);
    }

    private PostDto myPosts(PostDto posts, int offset, int limit, ModerationStatus modStatus, String email, boolean flag) {
        boolean isActive = true;
        int count;
        Page<Post> postPage;
        if(flag) {
            count = postRep.countAllByModerationStatusAndActive(email, isActive, modStatus);
        } else {
            count = postRep.countPostsByIsActiveAndModerationStatus(isActive, modStatus);
        }
        if (count == 0) {
            return setCountZero(posts);
        }
        posts.setCount(count);
        Pageable paging = getPageable(offset, limit, count);
        if(flag) {
            postPage = postRep.findMyPosts(email, isActive, modStatus, paging);
        } else {
            postPage = postRep.findPostByModeration(email, isActive, modStatus, paging);
        }
        return PostMapping.postMapping(posts, postPage, postVotes, postComment, recent);
    }

    private PostDto setCountZero(PostDto posts) {
            posts.setCount(0);
            posts.setPosts(List.of());
            return posts;
    }

    private Pageable getPageable(int offset, int limit, int count) {
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        Pageable paging = PageRequest.of(pageNumber, limit);
        return paging;
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
