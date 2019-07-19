package kiwi.blog.post.service;

import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.post.model.entity.Post;
import kiwi.blog.post.model.request.PostsRequest;
import kiwi.blog.post.model.request.SavePostRequest;
import kiwi.blog.post.model.response.PostResponse;
import kiwi.blog.post.model.response.PostsResponse;
import kiwi.blog.post.repository.PostRepository;
import kiwi.blog.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;

    @Autowired
    public PostService(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    public PostsResponse getPosts(PostsRequest postsRequest) {

        PostsResponse postsResponse = new PostsResponse();

        List<Post> posts = postRepository.selectPosts(postsRequest);
        long countPosts = postRepository.selectCountPosts(postsRequest);

        List<PostResponse> postResponses = BeanUtils.copyProperties(posts, PostResponse.class);

        int totalPages = (int) (countPosts / postsRequest.getLimit());
        if (countPosts % postsRequest.getLimit() > 0)
            totalPages = totalPages + 1;

        postsResponse.setContent(postResponses);
        postsResponse.setSize(postsRequest.getLimit());
        postsResponse.setTotalPages(totalPages);
        postsResponse.setNumber(postsRequest.getOffset());
        postsResponse.setTotalElements(countPosts);

        return postsResponse;
    }

    public PostResponse getPost(long postNo) {
        Post post = postRepository.findByPostNoAndUseYn(postNo, true);

        if (post == null) {
            return null;
        }

        return BeanUtils.copyProperties(post, PostResponse.class);
    }

    public void savePost(SavePostRequest savePostRequest) {

        Post post;

        if (savePostRequest.getPostNo() == null || savePostRequest.getPostNo() == 0) {

            post = BeanUtils.copyNullableProperties(savePostRequest, Post.class);
        } else {

            post = postRepository.findById(savePostRequest.getPostNo()).get();
            BeanUtils.copyProperties(savePostRequest, post);
        }

        savePostRequest.getTags().forEach(tagRequest -> tagRequest.getName().trim());

        tagService.saveTags(savePostRequest.getTags());
        post.setTags(tagService.getTags(savePostRequest.getTags()));

        postRepository.save(post);
    }
}
