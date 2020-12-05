package kiwi.blog.category.service;

import kiwi.blog.category.model.entity.Category;
import kiwi.blog.category.model.request.SaveCategoryRequest;
import kiwi.blog.category.model.request.SaveCategoryRequests;
import kiwi.blog.category.repository.CategoryRepository;
import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostService postService;

    @Transactional
    public List<Category> saveCategories(SaveCategoryRequests saveCategoryRequests) {

        //categoryRepository.removeCategories();

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
