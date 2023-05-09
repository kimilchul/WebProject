package com.springboot.domain.post;

import com.springboot.domain.comment.Comment;
import com.springboot.domain.comment.CommentRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @After
    public void cleanup() {
        commentRepository.deleteAll();
    }

    /*
    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시물";
        String content = "테스트 본문";

        commentRepository.save(Comment.builder()
                .postId(Long.valueOf((long)1))
        );

        //when
        List<Post> postsList = postRepository.findAll();

        //then
        Post posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

     */
}
