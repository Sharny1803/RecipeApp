package com.twoawesomeprogrammers.UserService.controller;

import com.twoawesomeprogrammers.UserService.model.User;
import com.twoawesomeprogrammers.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        User user1 = User.builder()
                .name(user.getName())
                .password(user.getPassword())
                .userName(user.getUserName())
                .about(user.getAbout())
                .photoUrl(user.getPhotoUrl())
                .instagramUrl(user.getInstagramUrl())
                .facebookUrl(user.getFacebookUrl())
                .twitterUrl(user.getTwitterUrl())
                .location(user.getLocation())
                .joinDate(user.getJoinDate())
                .recipes(user.getRecipes())
                .following(user.getFollowing())
                .followers(user.getFollowers())
                .build();
        return userRepository.save(user);
    }
}
