package com.springboot.web.controller;

import com.springboot.domain.comment.Comment;
import com.springboot.service.comment.CommentService;
import com.springboot.web.dto.posts.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/post/{id}/comment")
    public ResponseEntity<String> reply(@RequestBody CommentDto commentDto){
        Long commentId = commentService.save(commentDto);

        Optional<Comment> comment = commentService.findById(commentId);

        return comment.isPresent() ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
