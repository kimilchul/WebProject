package com.springboot.web.dto.posts;

import com.springboot.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private Long view;
    private int heart;
    private LocalDateTime modifiedDate;
    private int commentsNumber;

    public PostListResponseDto(Post entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.view = entity.getView();
        this.heart = entity.getHeart().size();
        this.modifiedDate = entity.getModifiedDate();
        this.commentsNumber = entity.getComments().size();
    }
}
