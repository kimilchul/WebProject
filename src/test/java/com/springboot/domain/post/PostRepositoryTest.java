package com.springboot.domain.post;

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
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup(){
        postRepository.deleteAll();
    }

    private String title = "title";
    private String content = "content";
    private String author = "author";

    @Test
    public void Find_All_By_Author(){
        //given
        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        List<Post> postsList = postRepository.findAllByAuthor(author);

        //then
        Post posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void Find_All(){
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        postRepository.save(Post.builder()
        .title(title)
        .content(content)
        .author(author)
        .build());

        //when
        List<Post> postsList = postRepository.findAll();

        //then
        Post posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void Paging_Test(){
        //given
        for(int i=0;i<12;i++){
            postRepository.save(Post.builder()
                    .content("content")
                    .title("title")
                    .author("author")
                    .build());
        }

        int pageNumber = 1;

        //when
        List<Post> pageLoad = postRepository.findPostsByPage(1);

        //then
        assertThat(pageLoad.size()).isEqualTo(10);
    }
}
