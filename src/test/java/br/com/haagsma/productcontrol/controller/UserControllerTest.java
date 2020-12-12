package br.com.haagsma.productcontrol.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.haagsma.productcontrol.model.Status;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.repository.StatusRepository;
import br.com.haagsma.productcontrol.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private StatusRepository statusRepository;

    @MockBean
    private MailService mailService;

    @MockBean
    private RoutesService routesService;


    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void contextLoads() {
        assertThat(userService).isNotNull();
        assertThat(jwtService).isNotNull();
        assertThat(userProfileService).isNotNull();
        assertThat(statusRepository).isNotNull();
        assertThat(mailService).isNotNull();
        assertThat(routesService).isNotNull();
    }

    @Test
    public void findAllShouldReturnAllUsers() throws Exception {
        this.mockMvc.perform(
                get("/user/find-all")
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void shouldSaveAnUser_thenReturnUserWithStatusAndId() throws Exception {


        Status s = new Status();
        s.setTag("ACTIVE");
        s.setDescription("asd");


        when(statusRepository.save(any(Status.class))).thenReturn(getStatus(s));
        when(statusRepository.findByTag("ACTIVE")).thenReturn(getStatus(s));

        statusRepository.save(s);


        assertThat(s.getId()).isNotNull();


        User user = new User();
        user.setName("Jhonatan");
        user.setPassword("123");
        user.setEmail("h@h.com");

        when(userService.register(any(User.class))).thenReturn(getUser(user));

        MvcResult mvcResult = this.mockMvc.perform(
                post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        ).andReturn();
        User userResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(userResponse.getPassword()).isNull();
        assertThat(userResponse.getId()).isNotNull();
        assertThat(userResponse.getStatus()).isNotNull();
        assertThat(userResponse.getStatus().getTag()).isEqualTo("ACTIVE");
    }

    @Test
    public void shouldRecieveAndUserToSave_thenReturnErrorEmailRequired() throws Exception {

        User user = new User();
        user.setName("Jhonatan");
        user.setPassword("123");
        MvcResult mvcResult = this.mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andDo(
                print()
        ).andExpect(
                content().string("Email cannot be empty")
        ).andReturn();
    }

    @Test
    public void shouldRecieveAndUserToSave_thenReturnErrorNameRequired() throws Exception {

        User user = new User();
        user.setPassword("123");
        user.setEmail("h@h.com");
        MvcResult mvcResult = this.mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andDo(
                print()
        ).andExpect(
                content().string("Name cannot be empty")
        ).andReturn();
    }

    @Test
    public void shouldRecieveAndUserToSave_thenReturnErrorPasswordRequired() throws Exception {

        User user = new User();
        user.setName("Jhonatan");
        user.setEmail("h@h.com");
        MvcResult mvcResult = this.mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andDo(
                print()
        ).andExpect(
                content().string("Password cannot be empty")
        ).andReturn();
    }

    Status getStatus(Status s) {
        s.setId(1L);
        return s;
    }
    User getUser(User user) {
        user.setId(1L);
        return user;
    }



}
