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
    private final PostService postService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, PostService postService) {
        this.categoryRepository = categoryRepository;
        this.postService = postService;
    }

    public CategoriesResponse getCategories(CategoriesRequest categoriesRequest) {

        CategoriesResponse categoriesResponse = new CategoriesResponse();

        List<Category> categories = categoryRepository.selectCategories(categoriesRequest);
        long countCategories = categoryRepository.selectCountCategories(categoriesRequest);

        List<CategoryResponse> categoryResponses = categories
                .stream()
                .map(this::copyCategoryEntityToResponse)
                .collect(Collectors.toList());

        int totalPages = (int) (countCategories / categoriesRequest.getLimit());
        if (countCategories % categoriesRequest.getLimit() > 0)
            totalPages = totalPages + 1;

        categoriesResponse.setContent(categoryResponses);
        categoriesResponse.setSize(categoryResponses.size());
        categoriesResponse.setTotalPages(totalPages);
        categoriesResponse.setNumber(categoriesRequest.getOffset());
        categoriesResponse.setTotalElements(countCategories);

        return categoriesResponse;
    }

    private CategoryResponse copyCategoryEntityToResponse(Category category) {

        CategoryResponse parentCategoryResponse = BeanUtils.copyNullableProperties(category, CategoryResponse.class);

        List<CategoryResponse> childrenCategoryResponses = new ArrayList<>();
        List<Category> childCategories = category.getChildrenCategory();
        if (childCategories != null) {
            childCategories.forEach(subCategory -> {
                CategoryResponse categoryResponse = copyCategoryEntityToResponse(subCategory);
                childrenCategoryResponses.add(categoryResponse);
            });
        }
        parentCategoryResponse.setChildrenCategoryResponses(childrenCategoryResponses);

        return parentCategoryResponse;
    }

    public CategoryResponse getCategoryResponse(long categoryNo) {

        Category category = categoryRepository.findById(categoryNo).orElse(null);

        if (category == null) return null;

        return BeanUtils.copyProperties(category, CategoryResponse.class);
    }

    @Transactional
    public List<Category> saveCategories(SaveCategoryRequests saveCategoryRequests) {

        if (saveCategoryRequests.getMoveCategoryNos() != null && saveCategoryRequests.getMoveCategoryNos().length > 0) {
            Arrays.stream(saveCategoryRequests.getMoveCategoryNos()).forEach(moveCategory ->
               postService.moveCategory(moveCategory.getPreviousCategoryNo(), moveCategory.getDestinationCategoryNo())
            );
        }

        if (saveCategoryRequests.getRemovedCategoryNos() != null && saveCategoryRequests.getRemovedCategoryNos().length > 0) {
            categoryRepository.removeCategories(saveCategoryRequests.getRemovedCategoryNos());
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

            BeanUtils.copyProperties(saveCategoryRequest, category);
        } else {
            category = BeanUtils.copyNullableProperties(saveCategoryRequest, Category.class);
        }

        if(rootCategory) {
            category.setParentCategory(null);
        }

        List<Category> childrenCategories = new ArrayList<>();
        List<SaveCategoryRequest> childCategoryRequests = saveCategoryRequest.getChildrenCategoryRequests();
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
