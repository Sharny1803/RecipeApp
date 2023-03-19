package com.twoawesomeprogrammers.recipeservice.controller;


import com.twoawesomeprogrammers.recipeservice.dto.RecipeDto;
import com.twoawesomeprogrammers.recipeservice.model.Recipe;
import com.twoawesomeprogrammers.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> findAllRecipes() {
        return recipeService.findAllRecipes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Recipe> findRecipeById(@PathVariable String id) {
        return recipeService.findRecipeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecipe(@RequestBody RecipeDto recipeDto) {
        recipeService.createRecipe(recipeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe deleteRecipeById(@PathVariable String id) {
        return recipeService.deleteRecipeById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe updateRecipe(@PathVariable String id,
                           @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }
}

