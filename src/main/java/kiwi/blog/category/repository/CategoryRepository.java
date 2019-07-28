package kiwi.blog.category.repository;

import kiwi.blog.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    @Modifying
    @Query("update Category c set c.useYn = 'N'")
    int removeCategories();

    @Modifying
    @Query("update Category c set c.useYn = 'N' where c.categoryNo in ?1")
    int removeCategoriesByCategoryNos(Long[] categoryNos);
}
