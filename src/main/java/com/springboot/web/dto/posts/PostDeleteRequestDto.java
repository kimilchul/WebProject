package com.springboot.web.dto.posts;

import com.springboot.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDeleteRequestDto {
    private String userName;
    private Long postId;
    @Builder
    public PostDeleteRequestDto(String userName, Long postId){
        this.userName=userName;
        this.postId=postId;
    }
}
