package skillbox.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import skillbox.dto.Mode;
import skillbox.dto.post.PostDTO;
import skillbox.dto.post.SinglePostDTO;
import skillbox.entity.Post;

import java.text.ParseException;

public interface PostService   {

    PostDTO getPosts(int offset, int limit, Mode mode);

    PostDTO searchPost(int offset, int limit, String query);

    PostDTO searchPostByTag(int offset, int limit, String tag);

    SinglePostDTO searchPostById(int postId);

    PostDTO searchPostByDate(int offset, int limit, String strDate) throws ParseException;
}
