package com.springboot.domain.photo;

import com.springboot.util.Categories;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PhotoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_list_id")
    private Long id;

    private Categories categories = Categories.DEFAULT;

    private Long photoId;

    private Long postId;

    private String photoOriginalName;

    @Builder
    public PhotoList(Long photoId, Long postId, String photoOriginalName){
        this.photoId = photoId;
        this.postId = postId;
        this.photoOriginalName = photoOriginalName;
    }
}
