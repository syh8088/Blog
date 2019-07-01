package kiwi.blog.category.repository;

import kiwi.blog.category.model.entity.Category;
import kiwi.blog.category.model.request.CategoriesRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom  {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public List<Category> selectCategories(CategoriesRequest categoriesRequest) {
        return null;
    }
}
