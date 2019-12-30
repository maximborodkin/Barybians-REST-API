package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.maxim.barybians.api.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String photo;
    private String status;
    private long birthDate;
    private int sex;
    private List<Long> roles;
    private List<PostDto> posts;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhotoUrl(photo);
        user.setStatus(status);
        user.setSex(sex);

        return user;
    }

    public static UserDto fromUser(User user, boolean hasRoles, boolean hasPosts){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoto(user.getPhotoUrl());
        userDto.setStatus(user.getStatus());
        userDto.setSex(user.getSex());
        userDto.setBirthDate(user.getBirthDate().getTime());

        if (hasRoles){
            List<Long> roles = new ArrayList<>();
            user.getRoles().forEach(role -> roles.add(role.getId()));
            userDto.setRoles(roles);
        }

        if (hasPosts){
            List<PostDto> posts = new ArrayList<>();
            user.getPosts().forEach(post -> posts.add(PostDto.fromPost(post, false, false)));
            Collections.reverse(posts);
            userDto.setPosts(posts);
        }
        return userDto;
    }
}
