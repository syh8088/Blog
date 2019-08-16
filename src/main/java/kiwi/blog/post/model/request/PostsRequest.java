package kiwi.blog.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import kiwi.blog.common.model.request.CommonRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsRequest extends CommonRequest {

    @ApiModelProperty(value = "제목", position = 10)
    private String title;

    @ApiModelProperty(value = "태그", position = 20)
    private String[] tags;

    @ApiModelProperty(value = "카테고리 번호", position = 30)
    private Long categoryNo;
}
