package br.com.haagsma.productcontrol.controller;


import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.repository.UserRepository;
import br.com.haagsma.productcontrol.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log
@DataJpaTest
public class UserControllerToTest {

    MockMvc mockMvc;
    UserService userService;
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserControllerTo(userService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveUser() throws Exception {
        User userToSave = new User();
        userToSave.setEmail("h@h.com");
        userToSave.setPassword("awd");
        MvcResult mvcResult = mockMvc.perform(post("/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToSave))
        )
                .andDo(this::seeResult)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userToSave)))
                .andReturn();
        System.out.println("Result " + mvcResult.getResponse().getContentAsString());
//                .andExpect(content().string());
    }

    @Test
    void findAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        User userToSave = new User();
        userToSave.setEmail("h@h.com");
        userToSave.setPassword("awd");
        userRepository.save(userToSave);
//        when(userService.save(userToSave)).thenReturn(userToSave);
        Assertions.assertThat(userToSave.getId()).isNotNull();
        log.info(userToSave.getId().toString());
        users.add(userToSave);
        mockMvc.perform(get("/users/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(this::seeResult)
//                .andExpect(content().json(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk());
    }

    void seeResult(MvcResult result) throws UnsupportedEncodingException {
        log.info("Result: " + result.getResponse().getContentAsString());
    }
}
