package com.twoawesomeprogrammers.UserService.repository;

import com.twoawesomeprogrammers.UserService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User,String> {
}
