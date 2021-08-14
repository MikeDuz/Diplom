package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.LikeAndModeration;
import skillbox.dto.WrapperResponse;
import skillbox.dto.post.ErrorResponse;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.PostRequest;
import skillbox.dto.post.SinglePostDto;
import skillbox.entity.Post;
import skillbox.entity.Tag;
import skillbox.entity.Tag2Post;
import skillbox.entity.User;
import skillbox.entity.enums.ModerationStatus;
import skillbox.entity.projection.PostProjection;
import skillbox.mapping.ModerationStatusMapping;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRep;
    private final PostVotesRepository postVotes;
    private final PostCommentsRepository postComment;
    private final TagRepository tagRep;
    private final Tag2PostRepository tag2PostRepository;
    private final UserRepository userRep;

    public static int TITLE_LENGTH = 3;
    public static int TEXT_LENGTH = 50;
    public static String DEFAULT_MODE = "recent";

    @Override
    @Transactional
    public PostDto getPosts(int offset, int limit, String mode) {
        PostDto postDTO = new PostDto();
        int postCount = postRep.countPosts(ModerationStatus.ACCEPTED, true, LocalDateTime.now(ZoneOffset.UTC));
        postDTO.setCount(postCount);
        if (postCount == 0) {
            return postDTO;
        }
        Pageable paging = getPageable(offset, limit, postCount, mode);
        List<PostProjection> posts = postRep.getPosts(ModerationStatus.ACCEPTED, true, LocalDateTime.now(ZoneOffset.UTC), paging);
        return PostMapping.postMapping(postDTO, posts);
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
        Pageable paging = getPageable(offset, limit, count, DEFAULT_MODE);
        ;
        List<PostProjection> queryPosts = postRep.findAllByTextContainsOrTitleContains(query, paging);
        if (queryPosts.size() == 0) {
            postDTO.setCount(0);
            return postDTO;
        }
        postDTO.setCount(count);
        return PostMapping.postMapping(postDTO, queryPosts);
    }

    @Override
    @Transactional
    public PostDto searchPostByTag(int offset, int limit, String tag) {
        PostDto posts = new PostDto();
        int count = tag2PostRepository.countAllByTagIdContains(tag);
        if (count == 0) {
            return setCountZero(posts);
        }
        Pageable paging = getPageable(offset, limit, count, DEFAULT_MODE);
        List<PostProjection> postByTag = tag2PostRepository.findPostByTag(tag, paging);
        posts.setCount(count);
        return PostMapping.postMapping(posts, postByTag);
    }

    @Override
    @Transactional
    public SinglePostDto searchPostById(int postId) {
        Post post = postRep.findPostById(postId);
        SinglePostDto postDTO = SinglePostDto.builder().build();
        if (!PostPublic.postPublic(post)) {
            return postDTO;
        }
        postRep.incrViewCount(postId);
        return PostMapping.createSinglePost(post, postVotes);
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
        Pageable paging = getPageable(offset, limit, count, DEFAULT_MODE);
        List<PostProjection> page = postRep.findAllByTime(date, paging);
        posts.setCount(count);
        return PostMapping.postMapping(posts, page);
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
    @Transactional
    public PostDto searchModeratedPost(int offset, int limit, String status, Principal principal) {
        ModerationStatus modStatus = ModerationStatusMapping.mapModStatus(status);
        PostDto posts = new PostDto();
        if (modStatus.equals(ModerationStatus.NEW)) {
            boolean isActive = true;
            int count = postRep.countPostsByIsActiveAndModerationStatus(isActive, modStatus);
            if (count == 0) {
                return setCountZero(posts);
            }
            posts.setCount(count);
            Pageable paging = getPageable(offset, limit, count, DEFAULT_MODE);
            List<PostProjection> postMod = postRep.getPosts(modStatus, true, LocalDateTime.now(ZoneOffset.UTC), paging);
            ;
            return PostMapping.postMapping(posts, postMod);
        }
        String email = principal.getName();
        boolean flag = false;
        return myPosts(posts, offset, limit, modStatus, email, flag);
    }

    @Override
    @Transactional
    @Modifying
    public WrapperResponse insertPost(PostRequest postRequest, Principal principal) {
        WrapperResponse wrapperResponse = wrapPostResponse(postRequest);
        if (wrapperResponse.isResult()) {
            User user = userRep.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            postRep.save(postForInsert(postRequest, user, ModerationStatus.NEW));
        }
        return wrapperResponse;
    }

    @Override
    @Transactional
    @Modifying
    public WrapperResponse changePost(int postId, PostRequest postRequest, Principal principal) {
        WrapperResponse wrapperResponse = wrapPostResponse(postRequest);
        if (wrapperResponse.isResult()) {
            LocalDateTime time = setTime(postRequest);
            boolean isActive = postRequest.getActive() == 1;
            Post post = postRep.findById(postId).get();
            ModerationStatus modStatus = setModerationStatus(principal, post);
            analiseTagToPost(postRequest, post);
            tag2PostRepository.saveAll(getTagsForPost(postRequest, post));
            postRep.updatePostWithModStatus(postId,
                    time,
                    isActive,
                    modStatus,
                    postRequest.getTitle(),
                    postRequest.getText());
        }
        return wrapperResponse;
    }

    @Override
    @Transactional
    public WrapperResponse moderatePost(LikeAndModeration likeAndMod, Principal principal) {
        User user =  userRep.findByEmail(principal.getName()).get();
        WrapperResponse wrapResp = new WrapperResponse();
        if(user.isModerator()) {
            switch (likeAndMod.getDecision()) {
                case ("accept") :
                    postRep.moderatePost(ModerationStatus.ACCEPTED, user, likeAndMod.getPostId());
                    break;
                case ("decline") :
                    postRep.moderatePost(ModerationStatus.DECLINED, user, likeAndMod.getPostId());
            }
            wrapResp.setResult(true);
            return wrapResp;
        }
        wrapResp.setResult(false);
        return wrapResp;
    }


    private void analiseTagToPost(PostRequest postRequest, Post post) {
        post.getTags().forEach(t -> {
            if (!postRequest.getTags().contains(t.getName())) {
                post.getTags().remove(t);
            } else {
                postRequest.getTags().remove(t.getName());
            }
        });
        postRequest.getTags().forEach(s -> tagMapper(s, post));
    }

    private ModerationStatus setModerationStatus(Principal principal, Post post) {
        ModerationStatus modStatus;
        if (principal.getName().equals(post.getUserId().getEmail())) {
            modStatus = ModerationStatus.NEW;
        } else {
            modStatus = post.getModerationStatus();
        }
        return modStatus;
    }

    private Post postForInsert(PostRequest postRequest, User user, ModerationStatus modStatus) {
        LocalDateTime time = setTime(postRequest);
        Post post = new Post();
        getTagsForPost(postRequest, post);
        post = PostMapping.insertPostMapper(time, postRequest, user, post, modStatus);
        return post;
    }

    private LocalDateTime setTime(PostRequest postRequest) {
        if (postRequest.getTimestamp().toLocalDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            return LocalDateTime.now(ZoneOffset.UTC);
        }
        return LocalDateTime.from(postRequest.getTimestamp().toLocalDateTime().atZone(ZoneOffset.UTC));
    }

    private WrapperResponse wrapPostResponse(PostRequest postRequest) {
        WrapperResponse wrapResp = new WrapperResponse();
        if (postRequest.getTitle().length() < TITLE_LENGTH) {
            ErrorResponse error = new ErrorResponse();
            error.setTitle("Слишком короткий заголовок");
            wrapResp.setErrors(error);
            wrapResp.setResult(false);
            return wrapResp;
        }
        if (postRequest.getText().length() < TEXT_LENGTH) {
            ErrorResponse error = new ErrorResponse();
            error.setText("Мало текста");
            wrapResp.setErrors(error);
            wrapResp.setResult(false);
            return wrapResp;
        }
        wrapResp.setResult(true);
        return wrapResp;
    }

    private PostDto myPosts(PostDto posts, int offset, int limit, ModerationStatus modStatus, String email, boolean flag) {
        boolean isActive = true;
        int count;
        List<PostProjection> postPage;
        if (flag) {
            count = postRep.countAllByModerationStatusAndActive(email, isActive, modStatus);
        } else {
            count = postRep.countPostsByIsActiveAndModerationStatus(isActive, modStatus);
        }
        if (count == 0) {
            return setCountZero(posts);
        }
        posts.setCount(count);
        Pageable paging = getPageable(offset, limit, count, DEFAULT_MODE);
        if (flag) {
            postPage = postRep.findMyPosts(email, isActive, modStatus, paging);
        } else {
            postPage = postRep.findPostByModeration(email, isActive, modStatus, paging);
        }
        return PostMapping.postMapping(posts, postPage);
    }

    private PostDto setCountZero(PostDto posts) {
        posts.setCount(0);
        posts.setPosts(List.of());
        return posts;
    }

    private Pageable getPageable(int offset, int limit, int count, String mode) {
        int pageNumber = SetPageNumber.setPage(offset, limit);
        limit = SetLimit.setLimit(offset, limit, count);
        switch (mode) {
            case ("recent"):
                return PageRequest.of(pageNumber, limit, Sort.by("time").descending());
            case ("popular"):
                return PageRequest.of(pageNumber, limit, Sort.by("commentCount").descending());
            case ("best"):
                return PageRequest.of(pageNumber, limit, Sort.by("likeCount").descending());
            case ("early"):
                PageRequest.of(pageNumber, limit, Sort.by("time"));
        }
        return PageRequest.of(pageNumber, limit, Sort.by("time"));
    }

    private PostDto myInactivePosts(PostDto posts, int offset, int limit, String email) {
        boolean isActive = false;
        int count = postRep.countMyPostByActive(isActive, email);
        if (count == 0) {
            posts.setCount(count);
            posts.setPosts(List.of());
            return posts;
        }
        Pageable paging = getPageable(offset, limit, count, DEFAULT_MODE);
        List<PostProjection> inactivePosts = postRep.findMyPosts(email, isActive, ModerationStatus.NEW, paging);
        return PostMapping.postMapping(posts, inactivePosts);
    }

    private Set<Tag2Post> getTagsForPost(PostRequest postRequest, Post post) {
        Set<Tag2Post> tag2Posts = new HashSet<>();
        postRequest.getTags().forEach(s -> {
            Tag2Post tag2Post = new Tag2Post();
            tag2Post.setPostId(post);
            tag2Post.setTagId(tagMapper(s, post));
            tag2Posts.add(tag2Post);
        });
        return tag2Posts;
    }

    private Tag tagMapper(String name, Post post) {
        Tag tag = new Tag();
        if (tagRep.existsByName(name)) {
            tag = tagRep.findOneByName(name);
        } else {
            tag.setName(name);
        }
        tag.addPost(post);
        post.addTags(tag);
        return tag;
    }

}
