package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.maxim.barybians.api.model.Post;
import ru.maxim.barybians.api.model.Role;
import ru.maxim.barybians.api.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private long lastVisit;
    private List<RoleDto> roles;
    private List<PostDto> posts;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhotoUrl(photo);
        user.setStatus(status);
        user.setBirthDate(new Date(birthDate));
        user.setSex(sex);
        user.setLastVisit(new Date(lastVisit));
        List<Role> userRoles = new ArrayList<>();
        roles.forEach(roleDto -> userRoles.add(roleDto.toRole()));
        user.setRoles(userRoles);
        List<Post> userPosts = new ArrayList<>();
        posts.forEach(postDto -> userPosts.add(postDto.toPost()));
        user.setPosts(userPosts);
        return user;
    }

    public static UserDto fromUser(User user, boolean hasRoles, boolean hasPosts, boolean hasComments){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoto("https://barybians.site/avatars/"+user.getPhotoUrl());
        userDto.setStatus(user.getStatus());
        userDto.setSex(user.getSex());
        userDto.setBirthDate(user.getBirthDate().getTime());

        if (hasRoles){
            List<RoleDto> roles = new ArrayList<>();
            user.getRoles().forEach(role -> roles.add(RoleDto.fromRole(role)));
            userDto.setRoles(roles);
        }

        if (hasPosts && user.getPosts() != null){
            List<PostDto> posts = new ArrayList<>();
            user.getPosts().forEach(post -> posts.add(PostDto.fromPost(post, false, false, hasComments, true, false)));
            Collections.reverse(posts);
            userDto.setPosts(posts);
        }
        return userDto;
    }
}
