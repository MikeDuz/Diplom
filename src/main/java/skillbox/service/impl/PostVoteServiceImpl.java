package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.WrapperResponse;
import skillbox.dto.LikeAndModeration;
import skillbox.entity.Post;
import skillbox.entity.PostVotes;
import skillbox.entity.User;
import skillbox.repository.PostRepository;
import skillbox.repository.PostVotesRepository;
import skillbox.repository.UserRepository;
import skillbox.service.PostVoteService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostVoteServiceImpl implements PostVoteService {

    private final PostVotesRepository postVotesRep;
    private final UserRepository userRep;
    private final PostRepository postRep;

    @Override
    @Transactional
    public WrapperResponse addLike2Post(Principal principal, LikeAndModeration postVotesRequest, int param) {
        WrapperResponse wrapResp = new WrapperResponse();
        int postId = postVotesRequest.getPostId();
        wrapResp.setResult(true);
        User user = userRep.findByEmail(principal.getName()).get();
        Optional<PostVotes> postVotesOpt = postVotesRep.findByPostIdAndUserId(postId, user.getId());
        if(postVotesOpt.isEmpty()) {
            postVotesRep.save(creatPostVotes(user, postId, param));
            return wrapResp;
        }
        if(postVotesOpt.get().getValue() == param) {
            wrapResp.setResult(false);
            return wrapResp;
        }
        postVotesRep.updatePostVotes(postVotesOpt.get().getId(), param, LocalDateTime.now(ZoneOffset.UTC));
        return wrapResp;
    }

    private PostVotes creatPostVotes(User user, int postId, int param) {
        Post post = postRep.findPostById(postId);
        PostVotes postVotes = new PostVotes();
        postVotes.setPostId(post);
        postVotes.setUserId(user);
        postVotes.setValue(param);
        postVotes.setTime(LocalDateTime.now(ZoneOffset.UTC));
        return postVotes;

    }
}
