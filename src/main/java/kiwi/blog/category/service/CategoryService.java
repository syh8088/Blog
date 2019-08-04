package kiwi.blog.category.service;

import kiwi.blog.category.model.entity.Category;
import kiwi.blog.category.model.request.CategoriesRequest;
import kiwi.blog.category.model.request.SaveCategoryRequest;
import kiwi.blog.category.model.request.SaveCategoryRequests;
import kiwi.blog.category.model.response.CategoriesResponse;
import kiwi.blog.category.model.response.CategoryResponse;
import kiwi.blog.category.repository.CategoryRepository;
import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    private PostService postService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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

    @Transactional
    public List<Category> saveCategories(SaveCategoryRequests saveCategoryRequests) {

        categoryRepository.removeCategories();

        if (saveCategoryRequests.getMoveCategoryNos() != null && saveCategoryRequests.getMoveCategoryNos().length > 0) {
            Arrays.stream(saveCategoryRequests.getMoveCategoryNos()).forEach(moveCategory ->
               postService.moveCategory(moveCategory.getPreviousCategoryNo(), moveCategory.getDestinationCategoryNo())
            );
        }

        if (saveCategoryRequests.getRemovedCategoryNos() != null && saveCategoryRequests.getRemovedCategoryNos().length > 0) {
            categoryRepository.removeCategoriesByCategoryNos(saveCategoryRequests.getRemovedCategoryNos());
        }

       return saveCategoryRequests.getCategoryRequests()
               .stream()
               .map(saveCategoryRequest -> categoryRepository.save(copyCategoryRequestToEntity(saveCategoryRequest, true)))
               .collect(Collectors.toList());
    }

    private Category copyCategoryRequestToEntity(SaveCategoryRequest saveCategoryRequest, boolean rootCategory) {

        Category category;

        if (saveCategoryRequest.getCategoryNo() != 0) {
            category = categoryRepository.findById(saveCategoryRequest.getCategoryNo()).get();
            category.setUseYn(true);

            BeanUtils.copyProperties(saveCategoryRequest, category);
        } else {
            category = BeanUtils.copyNullableProperties(saveCategoryRequest, Category.class);
        }

        if(rootCategory) {
            category.setParentCategory(null);
        }

        List<Category> childrenCategories = new ArrayList<>();
        List<SaveCategoryRequest> childCategoryRequests = saveCategoryRequest.getChildren();
        if (childCategoryRequests != null) {
            childCategoryRequests.forEach(childRequest -> {
                Category childCategory = copyCategoryRequestToEntity(childRequest, false);
                childCategory.setParentCategory(category);
                childrenCategories.add(childCategory);
            });
        }
        category.setChildrenCategory(childrenCategories);

        return category;
    }
}
