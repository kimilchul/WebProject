package com.springboot.web.controller;

import com.springboot.service.comment.CommentService;
import com.springboot.service.photo.PhotoService;
import com.springboot.web.dto.posts.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@RestController
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping(value = "/images/{originalPhotoName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String originalPhotoName) throws IOException {
        byte[] imageBytes = Files.readAllBytes(new File("images/" + originalPhotoName).toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
