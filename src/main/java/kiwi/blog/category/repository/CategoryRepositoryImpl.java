package kiwi.blog.category.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import kiwi.blog.category.model.entity.Category;
import kiwi.blog.category.model.entity.QCategory;
import kiwi.blog.category.model.request.CategoriesRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    QCategory qCategory = QCategory.category;


    @Override
    public List<Category> selectCategories(CategoriesRequest categoriesRequest) {

        JPQLQuery<Category> categoryJPQLQuery = selectCategoryJPQLQuery(categoriesRequest);

        return categoryJPQLQuery.fetch();
    }

    @Override
    public long selectCountCategories(CategoriesRequest categoriesRequest) {

        JPQLQuery<Category> categoryJPQLQuery = selectCategoryJPQLQuery(categoriesRequest);

        return categoryJPQLQuery.fetchCount();
    }

    private JPQLQuery<Category> selectCategoryJPQLQuery(CategoriesRequest categoriesRequest) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

       //if (categoriesRequest.getOnlyParent()) {
            booleanBuilder.and(qCategory.parentCategory.isNull());
        //}

        return from(qCategory).where(booleanBuilder);
    }
}
