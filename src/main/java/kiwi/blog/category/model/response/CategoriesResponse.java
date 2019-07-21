package kiwi.blog.category.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesResponse {

    @ApiModelProperty(value = "포스트 전체 페이지 수", position = 10)
    private long totalPages;

    @ApiModelProperty(value = "포스트 현재 페이지 숫자", position = 20)
    private long number;

    @ApiModelProperty(value = "포스트 전체 출력 수", position = 30)
    private long totalElements;

    @ApiModelProperty(value = "포스트 출력 수", position = 40)
    private long size;

    @ApiModelProperty(value = "포스트 출력 데이터", position = 50)
    private List<CategoryResponse> content;
}
