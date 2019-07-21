package kiwi.blog.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import kiwi.blog.post.model.entity.Post;
import kiwi.blog.post.model.entity.QPost;
import kiwi.blog.post.model.request.PostsRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */

    QPost qPost = QPost.post;

    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> selectPosts(PostsRequest postsRequest) {

        JPQLQuery<Post> postJPQLQuery = selectPostJPQLQuery(postsRequest);

        if (postsRequest.getOffset() != null && postsRequest.getLimit() != null) {
            postJPQLQuery
                    .limit(postsRequest.getLimit())
                    .offset((postsRequest.getOffset() - 1) * postsRequest.getLimit());
        }

        return postJPQLQuery.fetch();
    }

    @Override
    public long selectCountPosts(PostsRequest postsRequest) {
        JPQLQuery<Post> query = selectPostJPQLQuery(postsRequest);

        return query.fetchCount();
    }

    private JPQLQuery<Post> selectPostJPQLQuery(PostsRequest postsRequest) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (Strings.isNotEmpty(postsRequest.getTitle())) {
            booleanBuilder.and(qPost.title.like("%" + postsRequest.getTitle() + "%"));
        }

        return from(qPost).where(booleanBuilder);
    }

    @Override
    public long updateCategory(long previousCategoryNo, long destinationCategoryNo) {
        return update(qPost)
                .set(qPost.category.categoryNo, destinationCategoryNo)
                .where(qPost.category.categoryNo.eq(previousCategoryNo))
                .execute();
    }

}
