package kiwi.blog.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveCategoryRequests {

    @ApiModelProperty(value = "카테고리 정보", position = 10)
    private List<SaveCategoryRequest> categoryRequests;

    @ApiModelProperty(value = "삭제할 카테고리 번호", position = 20)
    private Long[] removedCategoryNos;

    @ApiModelProperty(value = "카테고리 글 이동", position = 30)
    private MoveCategory[] moveCategoryNos;

    @Getter
    @Setter
    public static class MoveCategory {
        long previousCategoryNo;
        long destinationCategoryNo;
    }
}
