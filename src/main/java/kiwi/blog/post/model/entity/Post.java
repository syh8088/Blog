package kiwi.blog.post.model.entity;

import kiwi.blog.common.model.entity.Common;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Post extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postNo;

    private String title;

    private String content;

    private Long viewCount;

}
