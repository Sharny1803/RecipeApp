package com.twoawesomeprogrammers.recipeservice.dto;

import com.twoawesomeprogrammers.recipeservice.model.Comments;
import com.twoawesomeprogrammers.recipeservice.model.Ingredients;
import com.twoawesomeprogrammers.recipeservice.model.Instructions;
import com.twoawesomeprogrammers.recipeservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private String name;
    private User user;
    private String about;
    private int cookTime;
    private int servings;
    private String origin;
    private Ingredients ingredients;
    private Instructions instructions;
    private Comments comments;

}
