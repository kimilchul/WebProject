package com.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.domain.post.Post;
import com.springboot.domain.post.PostRepository;
import com.springboot.web.controller.PostApiController;
import com.springboot.web.dto.posts.PostSaveRequestDto;
import com.springboot.web.dto.posts.PostUpdateRequestDto;
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

    private String title = "title";
    private String content = "content";
    private String author = "author";

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    private String createJsonString(String title, String content, String author){
        PostSaveRequestDto dto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        try{
            return new ObjectMapper().writeValueAsString(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_With_Picture() throws Exception {
        //given
        MockMultipartFile photo = new MockMultipartFile("photo", "1683082581.jpg", "image/jpeg", "photo1".getBytes());
        MockMultipartFile photo2 = new MockMultipartFile("photo", "photo2.jpg", "image/jpeg", "photo2".getBytes());

        String dtoJson = new ObjectMapper().writeValueAsString(createJsonString(title,content,author));

        //dto 전송 방식 json도 multipartFile로 보내야
        MockMultipartFile json = new MockMultipartFile
                ("dto", "jsondata", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(multipart(url)
                .file(photo)
                .file(photo2)
                .file(json)
                //file이 아닌 content 로 전송시 400 에러 발생
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        //then
        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_Update() throws Exception {
        //given
        String changedTitle = "changedTitle";
        String changedContent = "changedContent";


        String dtoJson = new ObjectMapper().writeValueAsString(createJsonString(title,content,author));

        MockMultipartFile json = new MockMultipartFile("dto", "jsondata", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";
        String updateUrl = "http://localhost:" + port + "/api/v1/update";

        //when
        mvc.perform(multipart(url)
                .file(json)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());


        PostUpdateRequestDto updateRequestDto = PostUpdateRequestDto.builder()
                .content(changedContent)
                .title(changedTitle)
                .build();

        Long id = postsRepository.findAllByAuthor("author").get(0).getId();

        mvc.perform(put(updateUrl + "/" + id)

        );

        //then
        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_Without_Picture() throws Exception {
        //given
        String dtoJson = new ObjectMapper().writeValueAsString(createJsonString(title,content,author));

        MockMultipartFile json = new MockMultipartFile("dto", "jsondata", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(multipart(url)
                .file(json)
                //file이 아닌 content 로 전송시 400 에러 발생
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        //then
        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_Delete() throws Exception {
        //given
        String dtoJson = new ObjectMapper().writeValueAsString(createJsonString(title,content,author));

        MockMultipartFile json = new MockMultipartFile("dto", "jsondata", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(multipart(url)
                .file(json)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());


        Long id = postsRepository.findAllByAuthor(author).get(0).getId();

        mvc.perform(delete(url+id));

        //then
        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0)).isNull();
    }
}
