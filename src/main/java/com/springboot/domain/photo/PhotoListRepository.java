package com.springboot.domain.photo;

import com.springboot.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoListRepository extends JpaRepository<PhotoList, Long> {
    List<PhotoList> findAllByPostId(Long postId);
}