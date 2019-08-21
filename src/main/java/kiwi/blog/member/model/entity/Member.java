package kiwi.blog.member.model.entity;

import kiwi.blog.common.enums.OauthType;
import kiwi.blog.common.model.entity.Common;
import kiwi.blog.role.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberNo;

    private String id;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    private String oauthId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_role_mapping", joinColumns = @JoinColumn(name = "memberNo"), inverseJoinColumns = @JoinColumn(name = "roleNo"))
    private List<Role> roles = new ArrayList<>();
}
