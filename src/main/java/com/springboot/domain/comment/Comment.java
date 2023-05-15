package com.springboot.domain.comment;

import com.springboot.domain.post.Post;
import com.springboot.web.dto.posts.CommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long postId;

    @Column
    private String commentAuthor;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentContent;

    @Column(name = "created_date")
    @CreatedDate
    private String createdDate;

    @Builder
    public Comment(String commentContent, String commentAuthor, Long postId){
        this.postId = postId;
        this.commentContent = commentContent;
        this.commentAuthor = commentAuthor;
        this.createdDate = LocalDateTime.now().toString();
    }
}
