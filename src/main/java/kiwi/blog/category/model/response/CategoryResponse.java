package kiwi.blog.category.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CategoryResponse {
    @ApiModelProperty(value = "카테고리 번호", position = 10)
    private long categoryNo;

    @ApiModelProperty(value = "제목", position = 20)
    private String name;

    @ApiModelProperty(value = "자식 카테고리", position = 30)
    private List<CategoryResponse> children;

    @ApiModelProperty(value = "전시 순서", position = 40)
    private long displayOrder;

    @ApiModelProperty(value = "등록일", position = 50)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정일", position = 60)
    private LocalDateTime updatedAt;
}
