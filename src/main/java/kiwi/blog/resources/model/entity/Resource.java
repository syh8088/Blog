package kiwi.blog.resources.model.entity;

import kiwi.blog.common.model.entity.Common;
import kiwi.blog.role.model.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "resources")
public class Resource extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long resourceNo;
    private String name;
    private Long orderNum;
    private String type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources_mapping", joinColumns = { @JoinColumn(name = "resource_no") }, inverseJoinColumns = { @JoinColumn(name = "role_no") } )
    private Set<Role> roleSet = new HashSet<>();
}
