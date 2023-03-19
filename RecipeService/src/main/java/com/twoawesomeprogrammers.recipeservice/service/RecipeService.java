package com.twoawesomeprogrammers.recipeservice.service;

import com.twoawesomeprogrammers.recipeservice.dto.RecipeDto;
import com.twoawesomeprogrammers.recipeservice.model.Recipe;
import com.twoawesomeprogrammers.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    public void createRecipe(@RequestBody RecipeDto recipeDto) {
        Recipe recipe = Recipe.builder()
                .name(recipeDto.getName())
                .user(recipeDto.getUser())
                .about(recipeDto.getAbout())
                .cookTime(recipeDto.getCookTime())
                .servings(recipeDto.getServings())
                .origin(recipeDto.getOrigin())
                .ingredients(recipeDto.getIngredients())
                .instructions(recipeDto.getInstructions())
                .comments(recipeDto.getComments())
                .build();
        recipeRepository.save(recipe);
        log.info("Recipe {} is saved", recipe.getId());
    }

    public Optional<Recipe> findRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    public Recipe deleteRecipeById(String id) {
        recipeRepository.deleteById(id);
        return null;
    }

    public Recipe updateRecipe(String id, Recipe newRecipe) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setName(newRecipe.getName());
                    recipe.setUser(newRecipe.getUser());
                    recipe.setAbout(newRecipe.getAbout());
                    recipe.setCookTime(newRecipe.getCookTime());
                    recipe.setServings(newRecipe.getServings());
                    recipe.setOrigin(newRecipe.getOrigin());
                    recipe.setIngredients(newRecipe.getIngredients());
                    recipe.setInstructions(newRecipe.getInstructions());
                    recipe.setComments(newRecipe.getComments());
                    return recipeRepository.save(recipe);
                })
                .orElseGet(() -> {
                    newRecipe.setId(id);
                    return recipeRepository.save(newRecipe);
                });
    }
}
