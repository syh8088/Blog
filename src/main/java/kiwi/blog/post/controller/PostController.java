package kiwi.blog.post.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kiwi.blog.post.model.request.PostsRequest;
import kiwi.blog.post.model.response.PostResponse;
import kiwi.blog.post.model.response.PostsResponse;
import kiwi.blog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Post", description = "블로그 본문")
@RestController
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @ApiOperation(value = "포스트 LIST 출력", notes = "포스트 LIST를 출력합니다.")
    public ResponseEntity<PostsResponse> getPosts(@ModelAttribute PostsRequest postsRequest) {

        return ResponseEntity.ok().body(postService.getPosts(postsRequest));
    }

    @GetMapping("{postNo}")
    @ApiOperation(value = "포스트 상세 조회", notes = "포스트를 조회합니다.")
    public ResponseEntity<PostResponse> getPost(@PathVariable long postNo) {
        PostResponse postResponse = postService.getPost(postNo);

        return (postResponse == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(postResponse);
    }
}
