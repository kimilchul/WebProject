package com.springboot.service.photo;


import com.springboot.domain.photo.Photo;
import com.springboot.domain.photo.PhotoList;
import com.springboot.domain.photo.PhotoListRepository;
import com.springboot.domain.photo.PhotoRepository;
import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.web.dto.posts.PhotoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoListService {
    private final PhotoListRepository photoListRepository;

    @Transactional
    public PhotoList save(PhotoListDto photoListDto) {
        return photoListRepository.save(
                PhotoList.builder()
                        .photoId(photoListDto.getPhotoId())
                        .postId(photoListDto.getPostId())
                        .photoOriginalName(photoListDto.getPhotoOriginalName())
                        .build()
        );
    }

    @Transactional
    public void deleteAllByPostId(Long postId) {
        photoListRepository.deleteAllByPostId(postId);
    }

    @Transactional
    public String[] findAllPhotoOriginalNameByPostId(Long postId) {
        return photoListRepository.findAllByPostId(postId).stream()
                .map(PhotoList::getPhotoOriginalName)
                .toArray(String[]::new);
    }
}