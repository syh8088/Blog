package kiwi.blog.post.service;

import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.post.model.entity.Post;
import kiwi.blog.post.model.request.SavePostRequest;
import kiwi.blog.post.repository.PostRepository;
import kiwi.blog.tag.service.TagService;
import kiwi.blog.tag.service.query.TagQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final TagQueryService tagQueryService;

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
        post.setTags(tagQueryService.getTags(savePostRequest.getTags()));

        postRepository.save(post);
    }

    @Transactional
    public long moveCategory(long previousCategoryNo, long destinationCategoryNo) {
        return postRepository.updateCategory(previousCategoryNo, destinationCategoryNo);
    }
}
