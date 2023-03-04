package com.twoawesomeprogrammers.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoawesomeprogrammers.UserService.model.User;
import com.twoawesomeprogrammers.UserService.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserServiceApplicationTests {
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

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userString))
                .andExpect(status().isCreated());
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

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user"));

        response.andExpect(status().isOk());
        Assertions.assertEquals(2, userRepository.findAll().size());

    }
}
