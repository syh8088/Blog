package kiwi.blog.tag.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagsResponse {
    @ApiModelProperty(value = "태그 개수", position = 10)
    private long totalCount;

    @ApiModelProperty(value = "태그", position = 20)
    private List<TagResponse> tagResponses;
}
