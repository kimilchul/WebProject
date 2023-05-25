package com.springboot.web;

import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void 메인페이지_로딩(){
        //when
        String body = this.restTemplate.getForObject("/",String.class);

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }

    @Test
    public void paging_test(){
        //given
        String body = this.restTemplate.getForObject("/",String.class);

        for(int i=0;i<5;i++){
            postRepository.save(Post.builder()
                    .content("content")
                    .title("title")
                    .author("author")
                    .build());
        }
        //when

        //then
    }

}
