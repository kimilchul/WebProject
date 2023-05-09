package com.springboot.web.controller;

import com.springboot.domain.photo.Photo;
import com.springboot.domain.post.Post;
import com.springboot.service.photo.PhotoListService;
import com.springboot.service.photo.PhotoService;
import com.springboot.service.posts.PostService;
import com.springboot.web.dto.PhotoDto;
import com.springboot.web.dto.posts.PhotoListDto;
import com.springboot.web.dto.posts.PostResponseDto;
import com.springboot.web.dto.posts.PostSaveRequestDto;
import com.springboot.web.dto.posts.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    private final PhotoService photoService;
    private final PhotoListService photoListService;

    @PostMapping(value = "/api/v1/post")//consumes = MediaType.APPLICATION_JSON_VALUE
    public Long save(@RequestPart("dto")
                                 PostSaveRequestDto dto,
                     @RequestPart("photo")
                             List<MultipartFile> photoDataList
    ){

        String filePath = "images/";
        Long postId = postService.save(dto);

        for(MultipartFile photoData : photoDataList){
            if(photoService.downloadPhoto(photoData)){
            }

            Long photoId = photoService.save(
                    PhotoDto.builder()
                            .filePath(filePath)
                            .fileSize(photoData.getSize())
                            .origFileName(photoData.getOriginalFilename())
                            .build()
            );

            photoListService.save(PhotoListDto.builder()
                    .postId(postId)
                    .photoId(photoId)
                    .photoOriginalName(photoData.getOriginalFilename())
                    .build());

        }

        return postId;
    }

    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }
}
