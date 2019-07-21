package kiwi.blog.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveCategoryRequest {

    @ApiModelProperty(value = "카테고리 번호", position = 10)
    private Long categoryNo;

    @ApiModelProperty(value = "카테고리 이름", required = true, position = 20)
    private String name;

    @ApiModelProperty(value = "진열 순서", position = 30)
    private long displayOrder;

    @ApiModelProperty(value = "하위 카테고리", position = 40)
    private List<SaveCategoryRequest> childrenCategoryRequests;
}
