package com.twoawesomeprogrammers.recipeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {
    private int step;
    private int amount;
    private String food;

}
