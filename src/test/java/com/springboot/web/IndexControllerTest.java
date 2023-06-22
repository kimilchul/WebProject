package com.springboot.web;

import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.web.dto.posts.PostSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class IndexControllerTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Find_Post_Out_Of_Index(){
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        postRepository.save(
                Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        String result = restTemplate.getForObject("/post/detail/2", String.class);

        //then
        assertThat(result).contains("해당 게시물이 없습니다");
    }
}
