package kiwi.blog.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRequest {
    @ApiModelProperty(value = "정렬 순서 값", position = 30)
    private String sort;

    @ApiModelProperty(value = "정렬 순서", position = 40)
    private String order;

    @ApiModelProperty(value = "조회결과 시작위치", position = 50)
    private Long offset = 1L;

    @ApiModelProperty(value = "조회결과 최대건수", position = 60)
    private Long limit = 10L;

    @ApiModelProperty(value = "상품 등록일 검색 시작일", position = 70)
    private String startDate;

    @ApiModelProperty(value = "상품 등록일 검색 종료일", position = 80)
    private String endDate;
}
