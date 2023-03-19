package com.twoawesomeprogrammers.userservice.repository;


import com.twoawesomeprogrammers.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User,String> {
}
