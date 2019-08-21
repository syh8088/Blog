package kiwi.blog.role.model.entity;

import kiwi.blog.common.model.entity.Common;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "role")
public class Role extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleNo;

    @Column(nullable = false)
    private String name;
}
