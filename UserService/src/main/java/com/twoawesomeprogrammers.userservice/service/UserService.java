package com.twoawesomeprogrammers.userservice.service;


import com.twoawesomeprogrammers.userservice.dto.UserDto;
import com.twoawesomeprogrammers.userservice.model.User;
import com.twoawesomeprogrammers.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(@RequestBody UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .userName(userDto.getUserName())
                .about(userDto.getAbout())
                .photoUrl(userDto.getPhotoUrl())
                .instagramUrl(userDto.getInstagramUrl())
                .facebookUrl(userDto.getFacebookUrl())
                .twitterUrl(userDto.getTwitterUrl())
                .location(userDto.getLocation())
                .joinDate(userDto.getJoinDate())
                .recipes(userDto.getRecipes())
                .following(userDto.getFollowing())
                .followers(userDto.getFollowers())
                .build();
        userRepository.save(user);
        log.info("User {} is saved", user.getId());
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public User deleteUserById(String id) {
        userRepository.deleteById(id);
        return null;
    }

    public User updateUser(String id, User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUserName(newUser.getUserName());
                    user.setAbout(newUser.getAbout());
                    user.setPhotoUrl(newUser.getPhotoUrl());
                    user.setInstagramUrl(newUser.getInstagramUrl());
                    user.setFacebookUrl(newUser.getFacebookUrl());
                    user.setTwitterUrl(newUser.getTwitterUrl());
                    user.setLocation(newUser.getLocation());
                    user.setJoinDate(newUser.getJoinDate());
                    user.setRecipes(newUser.getRecipes());
                    user.setFollowing(newUser.getFollowing());
                    user.setFollowers(newUser.getFollowers());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }
}
