package kiwi.blog.tag.service;

import kiwi.blog.tag.model.entity.Tag;
import kiwi.blog.tag.model.request.TagRequest;
import kiwi.blog.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

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
