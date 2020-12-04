package kiwi.blog.category.model.entity;

import kiwi.blog.common.model.entity.Common;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryNo;

    private String name;

    private long displayOrder;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_no")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> childrenCategory;
}
