package kiwi.blog.tag.service;

import kiwi.blog.common.etc.SystemConstants;
import kiwi.blog.common.model.enums.CacheName;
import kiwi.blog.common.service.CacheService;
import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.common.utils.JacksonUtils;
import kiwi.blog.tag.model.entity.Tag;
import kiwi.blog.tag.model.request.TagRequest;
import kiwi.blog.tag.model.response.TagResponse;
import kiwi.blog.tag.model.response.TagsResponse;
import kiwi.blog.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final CacheService cacheService;

    @Autowired
    public TagService(TagRepository tagRepository, CacheService cacheService) {
        this.tagRepository = tagRepository;
        this.cacheService = cacheService;
    }

    public TagsResponse getTags() {
        String cachedTags = cacheService.get(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY);

        if (cachedTags == null) {
            TagsResponse tagsResponse = new TagsResponse();
            tagsResponse.setTagResponses(BeanUtils.copyProperties(tagRepository.findAll(), TagResponse.class));
            tagsResponse.setTotalCount(tagRepository.findAll().size());
            cacheService.put(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY, tagsResponse);

            return tagsResponse;
        }

        return JacksonUtils.toForceModel(cachedTags, TagsResponse.class);
    }

    public List<Tag> getTags(List<TagRequest> tagRequests) {

        return tagRepository.findByNameIn(tagRequests
                .stream()
                .map(TagRequest::getName)
                .collect(Collectors.toList()));
    }

    @Transactional
    public void saveTags(List<TagRequest> tagRequests) {

        List<Tag> originTags = tagRepository
                .findByNameIn(tagRequests.stream()
                .map(tagRequest -> tagRequest.getName().trim())
                .filter(tagName -> tagName.length() > 0)
                .collect(Collectors.toList()));


        tagRepository.saveAll(tagRequests.stream()
                .filter(tagRequest ->
                        originTags.stream()
                                .noneMatch(originTag -> originTag.getName().trim().equals(tagRequest.getName().trim()))
                ).map(tagRequest -> {
                    Tag tag = new Tag();
                    tag.setName(tagRequest.getName().trim());

                    return tag;
                }).collect(Collectors.toList()));
    }
}
