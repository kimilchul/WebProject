package com.springboot.service.heart;

import com.springboot.domain.heart.Heart;
import com.springboot.domain.heart.HeartRepository;
import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.domain.user.User;
import com.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public boolean addHeart(String userEmail, Long postsId) {
        Optional<Post> postsWrapper = postRepository.findById(postsId);
        Post post = postsWrapper.get();
        Optional<User> userWrapper = userRepository.findByEmail(userEmail);
        User user = userWrapper.get();

        if(!isAlreadyHeart(user, post)) {
            heartRepository.save(new Heart(post,user));
            return true;
        }

        return false;
    }

    private boolean isAlreadyHeart(User user, Post post) {
        return heartRepository.findByUserAndPost(user,post).isPresent();
    }
}