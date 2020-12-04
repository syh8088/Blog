package kiwi.blog.resources.model.entity;

import io.swagger.annotations.ApiModelProperty;
import kiwi.blog.role.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SaveResourceRequest {

    @ApiModelProperty(value = "인가 자원 번호", position = 5)
    private String id;

    @ApiModelProperty(value = "인가 자원명 (ex: url 경우 /categories, method 방식 경우 io.security.corespringsecurity.aopsecurity.AopMethodService.methodSecured)", position = 10)
    private String name;

    @ApiModelProperty(value = "인가 자원 번호", position = 15)
    private String httpMethod;

    @ApiModelProperty(value = "인가 자원 적용 정렬 순서", position = 20)
    private int orderNum;

    @ApiModelProperty(value = "인가 자원 적용 유형 (ex: url, method, pointcut)", position = 25)
    private String type;

    @ApiModelProperty(value = "인가 역활 이름", position = 30)
    private String roleName;

    @ApiModelProperty(value = "인가 자원 번호", position = 35)
    private Set<Role> roleSet;
}
