package com.springboot.domain.post;

import com.springboot.domain.photo.PhotoList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

    long count();

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    @Query("SELECT p FROM Post p WHERE p.author LIKE %:author%")
    List<Post> findAllByAuthor(@Param("author") String author);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:content%")
    List<Post> findAllByContent(@Param("content") String content);

    @Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY post_id DESC) as row_num FROM post) AS subquery WHERE row_num <= (:pageNumber * 10) AND row_num > ((:pageNumber - 1) * 10)", nativeQuery = true)
    List<Post> findPostsByPage(@Param("pageNumber") Integer pageNumber);//, Pageable pageable);
}
