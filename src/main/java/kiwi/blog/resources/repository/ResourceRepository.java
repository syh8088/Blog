package kiwi.blog.resources.repository;


import kiwi.blog.resources.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("select r from Resource r join fetch r.roleSet where r.type = 'url' order by r.orderNum desc")
    List<Resource> findAllResources();
}
