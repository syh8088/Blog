package kiwi.blog.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesRequest {

    @ApiModelProperty(value = "최상위 카테고리만 조회", position = 10)
    private Boolean onlyParent = false;

}
