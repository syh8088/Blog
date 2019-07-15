package kiwi.blog.tag.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kiwi.blog.tag.model.response.TagsResponse;
import kiwi.blog.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Tag", description = "블로그 태그")
@RestController
@RequestMapping("tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("tags")
    @ApiOperation(value = "전체 태그 조회", notes = "전체 태그를 조회합니다.")
    public ResponseEntity<TagsResponse> getTag() {

        return ResponseEntity.ok().body(tagService.getTags());
    }
}
