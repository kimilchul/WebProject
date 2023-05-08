package com.springboot.web.controller;

import com.springboot.service.comment.CommentService;
import com.springboot.web.dto.posts.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/post/{id}/comment")
    public Long reply(@RequestBody CommentDto commentDto){
        return commentService.save(commentDto);
    }
}
