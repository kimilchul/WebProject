package com.springboot.domain.post;

import com.springboot.domain.comment.Comment;
import com.springboot.domain.comment.CommentRepository;
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
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @After
    public void cleanup() {
        commentRepository.deleteAll();
    }

    private String author = "author";
    private String content = "content";

    @Test
    public void Comment_Save() {
        //given
        commentRepository.save(Comment.builder()
                .commentAuthor(author)
                .commentContent(content)
                .build());

        //when
        List<Comment> commentList = commentRepository.findAll();

        //then
        Comment comment = commentList.get(0);
        assertThat(comment.getCommentContent()).isEqualTo(content);
        assertThat(comment.getCommentAuthor()).isEqualTo(author);
    }

    @Test
    public void Comment_Delete() {
        //given
        commentRepository.save(Comment.builder()
                .commentAuthor(author)
                .commentContent(content)
                .build());

        //when
        List<Comment> commentList = commentRepository.findAll();
        Comment comment = commentList.get(0);
        assertThat(comment.getCommentContent()).isEqualTo(content);
        assertThat(comment.getCommentAuthor()).isEqualTo(author);
        commentRepository.deleteAll();

        //then
        assertThat(commentRepository.findAll().isEmpty()).isEqualTo(true);

    }
}
