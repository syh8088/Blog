package kiwi.blog.common.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtUserRequest {
    private String member_id;
    private String user_name;
    private String member_name;
    private List<String> scope;
    private List<String> authorities;
    private List<JwtUserScopeRequest> roles;
    private Long member_seq;
    private String client_id;
    private Long exp;
    private String jti;
}
