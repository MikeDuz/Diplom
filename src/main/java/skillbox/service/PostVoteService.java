package skillbox.service;

import skillbox.dto.WrapperResponse;
import skillbox.dto.LikeAndModeration;

import java.security.Principal;

public interface PostVoteService {

    WrapperResponse addLike2Post(Principal principal, LikeAndModeration postId, int param);
}
