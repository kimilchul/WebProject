package com.springboot.web.dto.posts;

import com.springboot.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long postId;
    private String commentAuthor;
    private String commentContent;
    private String createdDate;

    public CommentDto(Comment comment){
        this.postId = comment.getPostId();
        this.commentAuthor = comment.getCommentAuthor();
        this.commentContent = comment.getCommentContent();
        this.createdDate = comment.getCreatedDate();
    }
}
