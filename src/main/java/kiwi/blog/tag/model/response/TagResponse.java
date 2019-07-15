package kiwi.blog.tag.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse {
    @ApiModelProperty(value = "태그 번호", position = 10)
    private Long tagNo;

    @ApiModelProperty(value = "태그 이름", position = 20)
    private String name;
}
