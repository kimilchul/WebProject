package com.springboot.web.dto.posts;

import com.springboot.domain.photo.Photo;
import com.springboot.domain.photo.PhotoList;
import com.springboot.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoListDto {
    Long photoId;
    Long postId;
    String photoOriginalName;
    @Builder
    public PhotoListDto(Long photoId,Long postId, String photoOriginalName){
        this.photoId = photoId;
        this.postId = postId;
        this.photoOriginalName = photoOriginalName;
    }
}
