package com.twoawesomeprogrammers.recipeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Instructions {
    private int number;
    private String description;
    private String photoOneUrl;
    private String photoTwoUrl;
    private String photoThreeUrl;
}
