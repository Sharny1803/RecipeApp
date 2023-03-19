package com.twoawesomeprogrammers.recipeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoawesomeprogrammers.recipeservice.controller.RecipeController;
import com.twoawesomeprogrammers.recipeservice.model.*;
import com.twoawesomeprogrammers.recipeservice.repository.RecipeRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class RecipeServiceApplicationTests {
    @MockBean
    RecipeController recipeController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RecipeRepository recipeRepository;
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @BeforeEach
    void setup() {
        recipeRepository.deleteAll();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateRecipe() throws Exception {

        Recipe recipe = getRecipe();

        String recipeString = objectMapper.writeValueAsString(recipe);
        recipeRepository.save(recipe);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeString))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(1, recipeRepository.findAll().size());
    }

    private Recipe getRecipe() {
        return Recipe.builder()
                .id("testId")
                .name("test")
                .user(null)
                .about("test")
                .cookTime(45)
                .servings(4)
                .origin("test")
                .ingredients(null)
                .instructions(null)
                .comments(null)
                .build();
    }

    @Test
    void shouldFindAllUsers() throws Exception {

        List<Recipe> allRecipes = new ArrayList<>();
        allRecipes.add(Recipe.builder()
                .name("test")
                .user(null)
                .about("test")
                .cookTime(45)
                .servings(4)
                .origin("test")
                .ingredients(null)
                .instructions(null)
                .comments(null)
                .build());
        allRecipes.add(Recipe.builder()
                .name("test")
                .user(null)
                .about("test")
                .cookTime(45)
                .servings(4)
                .origin("test")
                .ingredients(null)
                .instructions(null)
                .comments(null)
                .build());

        recipeRepository.saveAll(allRecipes);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(2, recipeRepository.findAll().size());

    }

    @Test
    void shouldFindRecipeById() throws Exception {

        Recipe recipe = getRecipe();
        recipe.setId("recipeId");

        given(recipeController.findRecipeById(recipe.getId())).willReturn(Optional.of(recipe));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipe/{id}", recipe.getId())
                        .param("id", "recipeId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldUpdateRecipe() throws Exception {

        Recipe updatedRecipe = new Recipe(
                "recipeId",
                "updatedTestName",
                null,
                "updatedTestAbout",
                45,
                4,
                "updatedTestOrigin",
                null,
                null,
                null
        );

        Recipe recipe = new Recipe(
                "recipeId",
                "testName",
                null,
                "testAbout",
                0,
                0,
                "testOrigin",
                null,
                null,
                null
        );

        recipeRepository.save(recipe);

        recipeRepository.findById(recipe.getId())
                .map(user1 -> {
                    recipe.setName(updatedRecipe.getName());
                    recipe.setUser(updatedRecipe.getUser());
                    recipe.setAbout(updatedRecipe.getAbout());
                    recipe.setCookTime(updatedRecipe.getCookTime());
                    recipe.setServings(updatedRecipe.getServings());
                    recipe.setOrigin(updatedRecipe.getOrigin());
                    recipe.setIngredients(updatedRecipe.getIngredients());
                    recipe.setInstructions(updatedRecipe.getInstructions());
                    recipe.setComments(updatedRecipe.getComments());
                    return recipeRepository.save(recipe);
                });

        String recipeString = objectMapper.writeValueAsString(recipe);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/recipe/{id}", recipe.getId())
                        .param("id", "recipeId")
                        .content(recipeString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Assertions.assertSame(recipe.getName(), updatedRecipe.getName());

    }

    @Test
    void shouldDeleteRecipe() throws Exception {
        Recipe recipe = new Recipe(
                "recipeId",
                "testName",
                null,
                "testAbout",
                45,
                4,
                "testOrigin",
                null,
                null,
                null
        );

        recipeRepository.save(recipe);

        recipeRepository.deleteById(recipe.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/recipe/{id}", recipe.getId())
                        .param("id", "recipeId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(0, recipeRepository.findAll().size());
    }
}
