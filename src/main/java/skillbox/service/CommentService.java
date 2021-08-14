package skillbox.service;

import skillbox.dto.WrapperResponse;
import skillbox.dto.comment.CommentDto;

import java.security.Principal;

public interface CommentService {

    WrapperResponse insertComment(CommentDto comment, Principal principal);
}
