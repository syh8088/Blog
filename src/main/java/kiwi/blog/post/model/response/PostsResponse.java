package kiwi.blog.post.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostsResponse {

    @ApiModelProperty(value = "포스트", position = 10)
    private long totalPages;

    @ApiModelProperty(value = "포스트", position = 20)
    private long totalElements;

    @ApiModelProperty(value = "포스트", position = 30)
    private long number;

    @ApiModelProperty(value = "포스트", position = 40)
    private long size;

    @ApiModelProperty(value = "포스트", position = 50)
    private List<PostResponse> postResponses;

}
