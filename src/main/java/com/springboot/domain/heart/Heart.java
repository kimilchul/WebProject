package com.springboot.domain.heart;

import com.springboot.domain.post.Post;
import com.springboot.domain.user.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "heart")
@Entity
public class Heart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Heart(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
