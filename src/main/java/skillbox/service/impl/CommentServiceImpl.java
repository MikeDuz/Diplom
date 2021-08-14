package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.WrapperResponse;
import skillbox.dto.comment.CommentDto;
import skillbox.entity.Post;
import skillbox.entity.PostComment;
import skillbox.entity.User;
import skillbox.mapping.CommentMapper;
import skillbox.repository.PostCommentsRepository;
import skillbox.repository.PostRepository;
import skillbox.repository.UserRepository;
import skillbox.service.CommentService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Value("${blog.commentLength}")
    private int commentLength;
    private final UserRepository userRep;
    private final PostCommentsRepository commentRep;
    private final PostRepository postRep;


    @Override
    @Transactional
    @Modifying
    public WrapperResponse insertComment(CommentDto comment, Principal principal) {
        WrapperResponse wrapResp = new WrapperResponse();
        if (comment.getText().length() < commentLength) {

        }
        if (!postRep.existsById(comment.getPostId()) && comment.getParentId() != null && !commentRep.existsById(comment.getParentId())) {

        }
        PostComment parentComment = (comment.getParentId() == null) ? null : commentRep.findById(comment.getParentId()).orElse(null);
        User user = userRep.findByEmail(principal.getName()).get();
        Post post = postRep.findPostById(comment.getPostId());
        PostComment postComment = commentRep.save(CommentMapper.commentMapper(comment, post, parentComment, user));
        wrapResp.setId(postComment.getId());
        return wrapResp;
    }
}
