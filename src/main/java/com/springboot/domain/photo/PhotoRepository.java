package com.springboot.domain.photo;

import com.springboot.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByOriginalFileName(String originalFileName);

    @Transactional
    void deleteByOriginalFileName(String originalFileName);
}