package com.springboot.web.dto.posts;

import com.springboot.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Long view;
    private int heart;
    private LocalDateTime modifiedDate;
    private List<CommentDto> comments;

    public PostDetailDto(Post entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.view = entity.getView();
        this.heart = entity.getHeart().size();
        this.modifiedDate = entity.getModifiedDate();
        this.comments = entity.getComments().stream()
                .map(CommentDto:: new)
                .collect(Collectors.toList());
    }
}
