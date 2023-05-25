package com.springboot.service.posts;

import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.web.dto.posts.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getId();
    }

    /* Views Counting */
    @Transactional
    public int updateView(Long id) {
        return postRepository.updateView(id);
    }

    @Transactional
    public PostDetailDto detailedView(Long id){
        Optional<Post> postWrapper = postRepository.findById(id);
        Post posts = postWrapper.get();

        return new PostDetailDto(posts);
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto){
        Post posts = postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. id = "+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    //@Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc(){
        return postRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    public Integer countPosts(){
        return (int)postRepository.count();
    }

    public List<PostListResponseDto> findByPage(Integer pageNumber){

        int startNumber = pageNumber; // Page number starts from 0
        int pageSize = 10; // Number of records per page

        Pageable pageable = PageRequest.of(startNumber, pageSize, Sort.by("id").ascending());

        return postRepository.findPostsByPage(pageNumber).stream()//,pageable).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Post posts = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        postRepository.delete(posts);
    }

    public PostResponseDto findById(Long id){
        Post entity = postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. id = "+id));
        return new PostResponseDto(entity);
    }


}
