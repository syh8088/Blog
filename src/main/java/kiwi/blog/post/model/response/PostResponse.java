package kiwi.blog.post.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    @ApiModelProperty(value = "포스트 번호", position = 10)
    private long postNo;

    @ApiModelProperty(value = "제목", position = 20)
    private String title;

    @ApiModelProperty(value = "본문", position = 30)
    private String content;

    @ApiModelProperty(value = "포스트 뷰 수", position = 40)
    private Long viewCount;

    @ApiModelProperty(value = "사용 여부", position = 50)
    private boolean useYn;

    @ApiModelProperty(value = "등록일", position = 60)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정일", position = 70)
    private LocalDateTime updatedAt;


}
