package com.twoawesomeprogrammers.recipeservice.repository;

import com.twoawesomeprogrammers.recipeservice.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
