package skillbox.service;

import skillbox.dto.WrapperResponse;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.PostRequest;
import skillbox.dto.post.SinglePostDto;
import skillbox.entity.enums.ModerationStatus;

import java.security.Principal;
import java.text.ParseException;

public interface PostService   {

    PostDto getPosts(int offset, int limit, String mode);

    PostDto searchPost(int offset, int limit, String query);

    PostDto searchPostByTag(int offset, int limit, String tag);

    SinglePostDto searchPostById(int postId);

    PostDto searchPostByDate(int offset, int limit, String strDate) throws ParseException;

    PostDto searchMyPosts(int offset, int limit, String status, Principal principal);

    PostDto searchModeratedPost(int offset, int limit, String status, Principal principal);

    WrapperResponse insertPost(PostRequest postRequest, Principal principal);

    WrapperResponse changePost(int postId, PostRequest postRequest, Principal principal);
}
