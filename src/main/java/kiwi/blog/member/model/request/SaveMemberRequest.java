package kiwi.blog.member.model.request;

import kiwi.blog.common.enums.OauthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveMemberRequest {
    private String id;
    private String password;
    private String name;
    private String email;
    private OauthType oauthType = OauthType.NONE;
}
