package com.twoawesomeprogrammers.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoawesomeprogrammers.userservice.controller.UserController;
import com.twoawesomeprogrammers.userservice.model.User;
import com.twoawesomeprogrammers.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserServiceApplicationTests {
    @MockBean
    UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateUser() throws Exception {

        User user = getUser();

        String userString = objectMapper.writeValueAsString(user);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userString))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(1, userRepository.findAll().size());
    }

    private User getUser() {
        return User.builder()
                .userName("test")
                .name("test")
                .about("test")
                .photoUrl("test")
                .instagramUrl("test")
                .facebookUrl("test")
                .twitterUrl("test")
                .location("test")
                .joinDate(LocalDate.now())
                .recipes(5)
                .following(123)
                .followers(321)
                .build();
    }

    @Test
    void shouldFindAllUsers() throws Exception {

        List<User> allUsers = new ArrayList<>();
        allUsers.add(User.builder()
                .userName("test")
                .name("test")
                .about("test")
                .photoUrl("test")
                .instagramUrl("test")
                .facebookUrl("test")
                .twitterUrl("test")
                .location("test")
                .joinDate(LocalDate.now())
                .recipes(5)
                .following(123)
                .followers(321)
                .build());
        allUsers.add(User.builder()
                .userName("test")
                .name("test")
                .about("test")
                .photoUrl("test")
                .instagramUrl("test")
                .facebookUrl("test")
                .twitterUrl("test")
                .location("test")
                .joinDate(LocalDate.now())
                .recipes(5)
                .following(123)
                .followers(321)
                .build());

        userRepository.saveAll(allUsers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(2, userRepository.findAll().size());

    }

    @Test
    void shouldFindUserById() throws Exception {

        User user = new User();
        user.setId("userId");

        given(userController.findUserById(user.getId())).willReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/{id}", user.getId())
                        .param("id", "userId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldUpdateUser() throws Exception {

        User updatedUser = new User(
                "testId",
                "updatedTestUserName",
                "updatedTestName",
                "updatedTestAbout",
                "updatedTestUrl",
                "updatedTestUrl",
                "updatedTestUrl",
                "updatedTestUrl",
                "updatedTestLocation",
                null,
                23,
                144,
                32
        );

        User user = new User(
                "testId",
                "testUserName",
                "testName",
                "testAbout",
                "testUrl",
                "testUrl",
                "testUrl",
                "testUrl",
                "testLocation",
                null,
                0,
                0,
                0);

        userRepository.save(user);

        userRepository.findById(user.getId())
                .map(user1 -> {
                    user.setUserName(updatedUser.getUserName());
                    user.setName(updatedUser.getName());
                    user.setAbout(updatedUser.getAbout());
                    user.setPhotoUrl(updatedUser.getPhotoUrl());
                    user.setInstagramUrl(updatedUser.getInstagramUrl());
                    user.setFacebookUrl(updatedUser.getFacebookUrl());
                    user.setTwitterUrl(updatedUser.getTwitterUrl());
                    user.setLocation(updatedUser.getLocation());
                    user.setJoinDate(updatedUser.getJoinDate());
                    user.setRecipes(updatedUser.getRecipes());
                    user.setFollowing(updatedUser.getFollowing());
                    user.setFollowers(updatedUser.getFollowers());
                    return userRepository.save(user);
                });

        String userString = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/user/{id}", user.getId())
                        .param("id", "userId")
                        .content(userString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Assertions.assertSame(user.getUserName(), updatedUser.getUserName());

    }

    @Test
    void shouldDeleteUser() throws Exception {
        User user = new User(
                "testId",
                "testUserName",
                "testName",
                "testAbout",
                "testUrl",
                "testUrl",
                "testUrl",
                "testUrl",
                "testLocation",
                null,
                0,
                0,
                0);

        userRepository.save(user);

        userRepository.deleteById(user.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/user/{id}", user.getId())
                        .param("id", "userId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(0, userRepository.findAll().size());
    }
}
