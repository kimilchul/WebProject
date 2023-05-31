package com.springboot.domain.post;

import com.springboot.domain.comment.Comment;
import com.springboot.domain.comment.CommentRepository;
import com.springboot.domain.heart.Heart;
import com.springboot.domain.heart.HeartRepository;
import com.springboot.domain.user.Role;
import com.springboot.domain.user.User;
import com.springboot.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HeartRepositoryTest {
    @Autowired
    HeartRepository heartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup() {
        heartRepository.deleteAll();
    }

    private String title = "title";
    private String content = "content";
    private String author = "author";

    @Test
    public void Heart_Save() {
        //given
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        User user = userRepository.save(User.builder()
                .email("email")
                .name("name")
                .picture("pic")
                .role(Role.USER)
                .build());

        Heart heart = heartRepository.save(
                Heart.builder()
                        .post(post)
                        .user(user)
                        .build());


        //when
        List<Heart> heartList = heartRepository.findAll();

        //then
        Heart heartFound = heartList.get(0);
        assertThat(heartFound.getId()).isEqualTo(heart.getId());
    }

    @Test
    public void Heart_Delete() {
        //given
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        User user = userRepository.save(User.builder()
                .email("email")
                .name("name")
                .picture("pic")
                .role(Role.USER)
                .build());

        Heart heart = heartRepository.save(
                Heart.builder()
                        .post(post)
                        .user(user)
                        .build());


        //when
        heartRepository.deleteAll();

        //then
        assertThat(heartRepository.findByUserAndPost(user,post).isPresent()).isFalse();
    }
}
