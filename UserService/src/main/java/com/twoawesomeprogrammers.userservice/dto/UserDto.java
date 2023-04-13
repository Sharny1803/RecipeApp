package com.twoawesomeprogrammers.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String email;
//    private String password;
    private String name;
    private String userName;
    private String about;
    private String photoUrl;
    private String instagramUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String location;
    private LocalDate joinDate;
    private int recipes;
    private int following;
    private int followers;

}

