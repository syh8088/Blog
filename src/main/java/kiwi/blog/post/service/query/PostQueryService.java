package kiwi.blog.post.service.query;

import kiwi.blog.category.service.query.CategoryQueryService;
import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.post.model.entity.Post;
import kiwi.blog.post.model.request.PostsRequest;
import kiwi.blog.post.model.response.PostResponse;
import kiwi.blog.post.model.response.PostsResponse;
import kiwi.blog.post.repository.PostRepository;
import kiwi.blog.tag.model.response.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostQueryService {

    private final PostRepository postRepository;
    private final CategoryQueryService categoryQueryService;

    public PostsResponse getPosts(PostsRequest postsRequest) {

        PostsResponse postsResponse = new PostsResponse();

        List<Post> posts = postRepository.selectPosts(postsRequest);
        long countPosts = postRepository.selectCountPosts(postsRequest);

        List<PostResponse> postResponses = BeanUtils.copyProperties(posts, PostResponse.class);

        postResponses.forEach(postResponse -> {
            postResponse.setTags(BeanUtils.copyProperties(postResponse.getTags(), TagResponse.class));
            postResponse.setCategory(categoryQueryService.copyCategoryEntityToResponse(
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
        if (post.getCategory() != null) postResponse.setCategory(categoryQueryService.copyCategoryEntityToResponse(post.getCategory()));

        return postResponse;
    }
}
