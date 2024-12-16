package com.springboot.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 인증 객체 가져오기
        Authentication authentication = (Authentication) request.getUserPrincipal();

        // 인증 객체가 OAuth2AuthenticationToken인지 확인
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

            // OAuth2User 가져오기
            OAuth2User user = authToken.getPrincipal();

            if (user != null) {
                // 로그인 사용자 정보를 request attribute에 추가
                request.setAttribute("userInfo", user.getAttributes());
            }
        }

        return true; // 다음 단계로 요청 진행
    }
}
