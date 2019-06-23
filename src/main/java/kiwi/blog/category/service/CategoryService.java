package kiwi.blog.category.service;

import kiwi.blog.category.model.request.CategoriesRequest;
import kiwi.blog.category.model.response.CategoriesResponse;
import kiwi.blog.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoriesResponse getCategories(CategoriesRequest categoriesRequest) {

        return null;
    }
}
