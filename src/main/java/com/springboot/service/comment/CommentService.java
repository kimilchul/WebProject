package com.springboot.service.comment;

import com.springboot.domain.comment.Comment;
import com.springboot.domain.comment.CommentRepository;
import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.web.dto.posts.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(CommentDto commentDto){
        Optional<Post> postWrapper = postRepository.findById(
                commentDto.getPostId()
        );

        Post post = postWrapper.get();

        return commentRepository.save(
                Comment.builder()
                        .postId(post)
                        .dto(commentDto)
                        .build()
        ).getId();
    }

}
