package kiwi.blog.category.service.query;

import kiwi.blog.category.model.entity.Category;
import kiwi.blog.category.model.request.CategoriesRequest;
import kiwi.blog.category.model.response.CategoriesResponse;
import kiwi.blog.category.model.response.CategoryResponse;
import kiwi.blog.category.repository.CategoryRepository;
import kiwi.blog.common.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoriesResponse getCategories(CategoriesRequest categoriesRequest) {

        CategoriesResponse categoriesResponse = new CategoriesResponse();

        List<Category> categories = categoryRepository.selectCategories(categoriesRequest);
        long countCategories = categoryRepository.selectCountCategories(categoriesRequest);

        List<CategoryResponse> categoryResponses = categories
                .stream()
                .filter(category -> category.getUseYn().equals(true))
                .map(this::copyCategoryEntityToResponse)
                .collect(Collectors.toList());

        categoriesResponse.setCategoryResponses(categoryResponses);
        categoriesResponse.setTotalCount(countCategories);

        return categoriesResponse;
    }

    public CategoryResponse copyCategoryEntityToResponse(Category category) {

        CategoryResponse parentCategoryResponse = BeanUtils.copyNullableProperties(category, CategoryResponse.class);

        List<CategoryResponse> childrenCategoryResponses = new ArrayList<>();
        List<Category> childCategories = category.getChildrenCategory();
        if (childCategories != null) {
            childCategories.forEach(subCategory -> {
                if (subCategory.getUseYn().equals(true)) {
                    CategoryResponse categoryResponse = copyCategoryEntityToResponse(subCategory);
                    childrenCategoryResponses.add(categoryResponse);
                }
            });
        }
        parentCategoryResponse.setChildren(childrenCategoryResponses);

        return parentCategoryResponse;
    }

    public CategoryResponse getCategoryResponse(long categoryNo) {

        Category category = categoryRepository.findById(categoryNo).orElse(null);

        if (category == null) return null;

        return BeanUtils.copyProperties(category, CategoryResponse.class);
    }
}
