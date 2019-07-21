package kiwi.blog.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import kiwi.blog.common.model.request.CommonRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesRequest extends CommonRequest {

    @ApiModelProperty(value = "최상위 카테고리만 조회", position = 10)
    private Boolean onlyParent = false;

}
