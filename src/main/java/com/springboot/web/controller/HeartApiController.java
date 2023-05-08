package com.springboot.web.controller;

import com.springboot.config.auth.LoginUser;
import com.springboot.config.auth.dto.SessionUser;
import com.springboot.domain.user.User;
import com.springboot.service.heart.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HeartApiController {
    private final HeartService heartService;

    @PostMapping("/api/v1/post/{postId}/heart")
    public ResponseEntity<String> addHeart(
            @LoginUser SessionUser user,
            @PathVariable Long postId) {

        boolean result = false;

        if (user != null) {
            result = heartService.addHeart(user.getEmail(), postId);
        }

        return result ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}