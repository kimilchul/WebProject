package com.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.web.controller.PostApiController;
import com.springboot.web.dto.posts.PostSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration()//
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PostRepository postsRepository;

    private MockMvc mvc;


    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }


    private String title = "title";
    private String content = "content";
    private String author = "author";

    private String dtoFactory() throws Exception{

        PostSaveRequestDto dto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        return  new ObjectMapper().writeValueAsString(dto);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_Save() throws Exception {
        //given
        MockMultipartFile photo = new MockMultipartFile("photo", "1683082581.jpg", "image/jpeg", "photo1".getBytes());
        MockMultipartFile photo2 = new MockMultipartFile("photo", "photo2.jpg", "image/jpeg", "photo2".getBytes());

        MockMultipartFile json = new MockMultipartFile("dto", "jsondata", "application/json", dtoFactory().getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(multipart(url)
                .file(photo)
                .file(photo2)
                .file(json)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        //then
        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("title");
        assertThat(all.get(0).getContent()).isEqualTo("content");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_Update() throws Exception {
        //given
        MockMultipartFile photo = new MockMultipartFile("photo", "1683082581.jpg", "image/jpeg", "photo1".getBytes());
        MockMultipartFile photo2 = new MockMultipartFile("photo", "photo2.jpg", "image/jpeg", "photo2".getBytes());
        MockMultipartFile photo3 = new MockMultipartFile("photo", "photo2.jpg", "image/jpeg", "photo2".getBytes());


        MockMultipartFile json = new MockMultipartFile("dto", "jsondata", "application/json", dtoFactory().getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(multipart(url)
                .file(photo)
                .file(photo2)
                .file(json)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        //then
        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("title");
        assertThat(all.get(0).getContent()).isEqualTo("content");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_Delete() throws Exception {
        //given
        MockMultipartFile json = new MockMultipartFile("dto", "jsondata", "application/json", dtoFactory().getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(multipart(url)
                .file(json)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        //then
        List<Post> all = postsRepository.findAll();
        Post post = all.get(0);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("content");

        mvc.perform(delete(url+"/"+post.getId()));
    }
}
