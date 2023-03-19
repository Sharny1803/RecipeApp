package com.twoawesomeprogrammers.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String userName;
//    private String password;
    private String name;
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
