package com.solera.backendcursojava.repositories;

import com.solera.backendcursojava.entities.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/***
 * Paging and Sorting = CRUD + paging and sorting
 */
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {
    /***
     * Get user posts, showing the newest ones first.
     * @param userId user id
     * @return user posts
     */
    List<PostEntity> getByUserIdOrderByCreatedAtDesc (long userId);

    /***
     * In order to get last 2O public posts
     * @param exposureId If the ID changes in the database
     * @param now
     * @return Last public posts
     * @Note nativeQuery: native query from sql
     */
    @Query(value = "SELECT * FROM posts p WHERE p.exposure_id = :exposure AND p.expires_at > :now ORDER BY p.created_at DESC LIMIT 20", nativeQuery = true)
    List<PostEntity> getLastPublicPosts(@Param("exposure") long exposureId, @Param("now") Date now);

    PostEntity findByPostId(String postId);
}
