package kiwi.blog.post.repository;

import kiwi.blog.post.model.entity.Post;
import kiwi.blog.post.model.request.PostsRequest;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> selectPosts(PostsRequest postsRequest);
    long selectCountPosts(PostsRequest postsRequest);
}
