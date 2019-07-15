package kiwi.blog.post.repository;

import kiwi.blog.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Post findByPostNoAndUseYn(long postNo, boolean useYn);
}
