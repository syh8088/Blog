package kiwi.blog.tag.repository;

import kiwi.blog.tag.model.entity.Tag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */

    public TagRepositoryImpl() {
        super(Tag.class);
    }

}
