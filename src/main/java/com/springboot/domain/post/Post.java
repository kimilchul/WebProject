package com.springboot.domain.post;

import com.springboot.domain.BaseTimeEntity;
import com.springboot.domain.heart.Heart;
import com.springboot.domain.comment.Comment;
import com.springboot.domain.photo.PhotoList;
import com.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
public class Post extends BaseTimeEntity {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userId;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column
    private String author;

    @Column(columnDefinition = "integer default 0")
    private Long view;
/*
    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PhotoList> photoList;

 */

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Heart> heart = new HashSet<>();

    @Builder
    public Post(String title,User userId, String content, String author){
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.author = author;
        this.view = 0L;
    }

    public void update(String title,String content){
        this.title = title;
        this.content = content;
    }
}
