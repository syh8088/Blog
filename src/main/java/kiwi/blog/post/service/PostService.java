package kiwi.blog.post.service;

import kiwi.blog.category.service.CategoryService;
import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.post.model.entity.Post;
import kiwi.blog.post.model.request.PostsRequest;
import kiwi.blog.post.model.request.SavePostRequest;
import kiwi.blog.post.model.response.PostResponse;
import kiwi.blog.post.model.response.PostsResponse;
import kiwi.blog.post.repository.PostRepository;
import kiwi.blog.tag.model.response.TagResponse;
import kiwi.blog.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;

    @Autowired
    private CategoryService categoryService;

    public PostsResponse getPosts(PostsRequest postsRequest) {

        PostsResponse postsResponse = new PostsResponse();

        List<Post> posts = postRepository.selectPosts(postsRequest);
        long countPosts = postRepository.selectCountPosts(postsRequest);

        List<PostResponse> postResponses = BeanUtils.copyProperties(posts, PostResponse.class);

        postResponses.forEach(postResponse -> {
            postResponse.setTags(BeanUtils.copyProperties(postResponse.getTags(), TagResponse.class));
            postResponse.setCategory(categoryService.copyCategoryEntityToResponse(
                    posts.stream()
                            .filter(p -> p.getPostNo() == postResponse.getPostNo())
                            .findAny()
                            .get()
                            .getCategory()));
        });

        int totalPages = (int) (countPosts / postsRequest.getLimit());
        if (countPosts % postsRequest.getLimit() > 0)
            totalPages = totalPages + 1;

        postsResponse.setPostResponses(postResponses);
        postsResponse.setSize(postResponses.size());
        postsResponse.setTotalPages(totalPages);
        postsResponse.setNumber(postsRequest.getOffset());
        postsResponse.setTotalElements(countPosts);

        return postsResponse;
    }

    public PostResponse getPost(long postNo) {

        Post post = postRepository.findByPostNoAndUseYn(postNo, true);

        if (post == null) return null;

        PostResponse postResponse = BeanUtils.copyProperties(post, PostResponse.class);

        if (post.getTags() != null) postResponse.setTags(BeanUtils.copyProperties(post.getTags(), TagResponse.class));
        if (post.getCategory() != null) postResponse.setCategory(categoryService.copyCategoryEntityToResponse(post.getCategory()));

        return postResponse;
    }

    @Transactional
    public void savePost(SavePostRequest savePostRequest) {

        Post post;

        if (savePostRequest.getPostNo() == null || savePostRequest.getPostNo() == 0) {

            post = BeanUtils.copyNullableProperties(savePostRequest, Post.class);
            post.setViewCount(0L);
        } else {

            post = postRepository.findById(savePostRequest.getPostNo()).get();
            BeanUtils.copyProperties(savePostRequest, post);
        }

        savePostRequest.getTags().forEach(tagRequest -> tagRequest.getName().trim());

        tagService.saveTags(savePostRequest.getTags());
        post.setTags(tagService.getTags(savePostRequest.getTags()));

        postRepository.save(post);
    }

    @Transactional
    public long moveCategory(long previousCategoryNo, long destinationCategoryNo) {
        return postRepository.updateCategory(previousCategoryNo, destinationCategoryNo);
    }
}
