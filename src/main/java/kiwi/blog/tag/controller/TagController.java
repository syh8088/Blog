package kiwi.blog.tag.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kiwi.blog.tag.model.response.TagsResponse;
import kiwi.blog.tag.service.query.TagQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Tag", description = "블로그 태그")
@RestController
@RequiredArgsConstructor
@RequestMapping("tags")
public class TagController {

    private final TagQueryService tagQueryService;

    @GetMapping()
    @ApiOperation(value = "전체 태그 조회", notes = "전체 태그를 조회합니다.")
    public ResponseEntity<TagsResponse> getTag() {

        return ResponseEntity.ok().body(tagQueryService.getTags());
    }
}
