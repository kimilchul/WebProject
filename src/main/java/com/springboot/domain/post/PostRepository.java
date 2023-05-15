package com.springboot.domain.post;

import com.springboot.domain.photo.PhotoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    @Query("SELECT p FROM Post p WHERE p.author LIKE %:author%")
    List<Post> findAllByAuthor(@Param("author") String author);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:content%")
    List<Post> findAllByContent(@Param("content") String content);
}
