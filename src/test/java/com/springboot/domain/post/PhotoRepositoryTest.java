package com.springboot.domain.post;


import com.springboot.domain.photo.Photo;
import com.springboot.domain.photo.PhotoRepository;
import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
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
public class PhotoRepositoryTest {
    @Autowired
    PhotoRepository photoRepository;

    @After
    public void cleanup(){
        photoRepository.deleteAll();
    }

    @Test
    public void Photo_Save(){
        //given
        Photo photo = Photo.builder()
                .fileSize(Long.valueOf(1))
                .filePath("path")
                .originalFileName("name")
                .build();

        //when
        photoRepository.save(photo);

        //then
        assertThat(photoRepository.findByOriginalFileName("name").getOriginalFileName()).isEqualTo(photo.getOriginalFileName());
    }

    @Test
    public void Photo_Delete(){
        //given
        Photo photo = Photo.builder()
                .fileSize(Long.valueOf(1))
                .filePath("path")
                .originalFileName("name")
                .build();

        //when
        photoRepository.save(photo);
        assertThat(photoRepository.findByOriginalFileName("name").getOriginalFileName()).isEqualTo(photo.getOriginalFileName());
        photoRepository.deleteByOriginalFileName("name");

        //then
        assertThat(photoRepository.findByOriginalFileName("name")).isNull();
    }
}
