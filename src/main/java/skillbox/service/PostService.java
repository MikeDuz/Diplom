package skillbox.service;

import skillbox.dto.Mode;
import skillbox.dto.post.PostDto;
import skillbox.dto.post.SinglePostDto;

import java.security.Principal;
import java.text.ParseException;

public interface PostService   {

    PostDto getPosts(int offset, int limit, Mode mode);

    PostDto searchPost(int offset, int limit, String query);

    PostDto searchPostByTag(int offset, int limit, String tag);

    SinglePostDto searchPostById(int postId);

    PostDto searchPostByDate(int offset, int limit, String strDate) throws ParseException;

    PostDto searchMyPosts(int offset, int limit, String status, Principal principal);
}
