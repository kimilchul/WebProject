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

    @GetMapping("/{pageNumber}")
    public String index(@PathVariable Integer pageNumber, Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",
                postService.findByPage(pageNumber)
        );

        if(user!=null){
            model.addAttribute("userName",user.getName());
        }

        int postsPerPage=10;
        int totalPosts = postService.countPosts();
        int totalPages = totalPosts/postsPerPage;
        System.out.println(totalPages + "is the total pages and "+totalPosts+" TP "+postsPerPage);
        if(totalPosts%postsPerPage!=0){
            totalPages+=1;
        }
        System.out.println("calculateasd asx sex "+totalPages);
        Pagination pagination = Pagination.builder()
                .endPage(totalPages)
                .build();

        model.addAttribute("pagination",pagination);

        return "index";
    }

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",
                postService.findByPage(Integer.valueOf(1))
        );
        if(user!=null){
            model.addAttribute("userName",user.getName());
        }

        int postsPerPage=5;
        int totalPages = (int)Math.floorDiv(postService.countPosts(),postsPerPage);
        if(postService.countPosts()%postsPerPage!=0){
            totalPages++;
        }

        Pagination pagination = Pagination.builder()
                .endPage(totalPages)
                .build();

        model.addAttribute("pagination",pagination);

        return "index";
    }

    @GetMapping("/post/save")
    public String postsSave(){
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model){
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post",dto);

        return "post-update";
    }

    @GetMapping("/post/detail/{id}")
    public String postsDetail(@PathVariable Long id, Model model,@LoginUser SessionUser user) {

        PostDetailDto dto = postService.detailedView(id);

        String[] photoOriginalNameList = photoListService.findAllPhotoOriginalNameByPostId(id);

        List<CommentDto> comments = dto.getComments();

        postService.updateView(id);

        model.addAttribute("post", dto);

        model.addAttribute("doesWahat",1);

        model.addAttribute("photoList", photoOriginalNameList);

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
        }

        return "post-detail";
    }
}
