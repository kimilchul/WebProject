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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    private final PhotoService photoService;
    private final PhotoListService photoListService;

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @PostMapping(value = "/api/v1/post")//consumes = MediaType.APPLICATION_JSON_VALUE
    public Long save(@RequestPart("dto")
                                 PostSaveRequestDto dto,
                     @RequestPart("photo")
                             List<MultipartFile> photoDataList,
                     @RequestParam("g-recaptcha-response")
                         String recaptchaResponse
    ){
        // 1. reCAPTCHA 검증
        boolean recaptchaValid = verifyRecaptcha(recaptchaResponse);
        if (!recaptchaValid) {
            throw new RuntimeException("reCAPTCHA validation failed");
        }


        String filePath = "images/";
        Long postId = postService.save(dto);

        for(MultipartFile photoData : photoDataList){
            if(photoService.downloadPhoto(photoData)){
            }

            Long photoId = photoService.save(
                    PhotoDto.builder()
                            .filePath(filePath)
                            .fileSize(photoData.getSize())
                            .originalFileName(photoData.getOriginalFilename())
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

    // reCAPTCHA 검증 메서드
    private boolean verifyRecaptcha(String recaptchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", recaptchaSecret);
        params.add("response", recaptchaResponse);

        // Google reCAPTCHA API 호출
        ResponseEntity<Map> response = restTemplate.postForEntity(url, params, Map.class);
        Map<String, Object> body = response.getBody();
        return body != null && (Boolean) body.get("success");
    }

    @PutMapping("/api/v1/post/{id}")
    public Long update(@RequestPart("dto")
                               PostUpdateRequestDto dto,
                       @RequestPart("photo")
                                   List<MultipartFile> photoDataList) {

        String filePath = "images/";

        photoListService.deleteAllByPostId(dto.getPostId());

        for(MultipartFile photoData : photoDataList){
            if(photoService.downloadPhoto(photoData)){
            }

            Long photoId = photoService.save(
                    PhotoDto.builder()
                            .filePath(filePath)
                            .fileSize(photoData.getSize())
                            .originalFileName(photoData.getOriginalFilename())
                            .build()
            );

            photoListService.save(PhotoListDto.builder()
                    .postId(dto.getPostId())
                    .photoId(photoId)
                    .photoOriginalName(photoData.getOriginalFilename())
                    .build());
        }

        System.out.println(
                dto.getPostId()+" is pos id "+dto.getTitle()+" is title"
        );

        return postService.update(dto.getPostId(), dto);
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
