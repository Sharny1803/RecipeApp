package com.twoawesomeprogrammers.recipeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Recipes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    private String id;
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
