package kiwi.blog.category.repository;

import kiwi.blog.category.model.entity.Category;
import kiwi.blog.category.model.request.CategoriesRequest;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> selectCategories(CategoriesRequest categoriesRequest);
}
