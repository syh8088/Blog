package kiwi.blog.category.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesResponse {

    @ApiModelProperty(value = "포스트 전체 출력 수", position = 10)
    private long totalCount;

    @ApiModelProperty(value = "포스트 출력 데이터", position = 20)
    private List<CategoryResponse> categoryResponses;
}
