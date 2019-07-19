package kiwi.blog.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import kiwi.blog.tag.model.request.TagRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SavePostRequest {

    @ApiModelProperty(value = "포스트번호", position = 5)
    private Long postNo;

    @ApiModelProperty(value = "제목", required = true, position = 10)
    private String title;

    @ApiModelProperty(value = "본문", required = true, position = 15)
    private String body;

    @ApiModelProperty(value = "카테고리 번호", position = 25)
    private long categoryNo;

    @ApiModelProperty(value = "태그", position = 30)
    private List<TagRequest> tags;
}
