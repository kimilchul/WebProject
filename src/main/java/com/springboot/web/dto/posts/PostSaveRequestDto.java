package com.springboot.web.dto.posts;

import com.springboot.domain.post.Post;
import com.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostSaveRequestDto(String title, String content, String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public Post toEntity(User user){
        return Post.builder()
                .title(title)
                .userId(user)
                .content(content)
                .author(author)
                .build();
    }
}
