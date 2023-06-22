package com.springboot.web.controller;

import com.springboot.config.auth.LoginUser;
import com.springboot.config.auth.dto.SessionUser;
import com.springboot.domain.post.Pagination;
import com.springboot.service.photo.PhotoListService;
import com.springboot.service.posts.PostService;
import com.springboot.web.dto.posts.CommentDto;
import com.springboot.web.dto.posts.PostDetailDto;
import com.springboot.web.dto.posts.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final PhotoListService photoListService;

    private final HttpSession httpSession;

    @GetMapping("/main/{pageNumber}")
    public String index(@PathVariable Integer pageNumber, Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",
                postService.findByPage(pageNumber)
        );

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        int postsPerPage = 10;
        int totalPosts = postService.countPosts();
        int totalPages = totalPosts / postsPerPage;
        if (totalPosts % postsPerPage != 0) {
            totalPages += 1;
        }
        Pagination pagination = Pagination.builder()
                .currentPage(pageNumber)
                .endPage(totalPages)
                .build();

        model.addAttribute("pagination", pagination);

        return "index";
    }

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        return "redirect:/main/1";
    }

    @GetMapping("/post/save")
    public String postSave(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "post-update";
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        try {
            PostDetailDto dto = postService.detailView(id);

            List<CommentDto> comments = dto.getComments();
            model.addAttribute("post", dto);
            if (comments != null && !comments.isEmpty()) {
                model.addAttribute("comments", comments);
            }
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }

        String[] photoOriginalNameList = photoListService.findAllPhotoOriginalNameByPostId(id);

        postService.updateView(id);

        model.addAttribute("photoList", photoOriginalNameList);

        
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
        }

        return "post-detail";
    }
}
