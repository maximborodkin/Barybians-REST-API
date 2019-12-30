package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.maxim.barybians.api.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PostDto implements Comparable<PostDto>{

    private long id;
    private UserDto user;
    private String title;
    private String text;
    private long time;
    private int edited;
    private int likesCount;
    private List<UserDto> likedUsers;

    public Post toPost(){
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setText(text);
        post.setTime(new Date(time));
        post.setEdited(edited);

        return post;
    }

    public static PostDto fromPost(Post post, boolean hasUser, boolean hasLikedUsers){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        if (hasUser) {
            postDto.setUser(UserDto.fromUser(post.getUser(), false, false));
        }
        postDto.setTitle(post.getTitle());
        postDto.setText(post.getText());
        postDto.setTime(post.getTime().getTime());
        postDto.setEdited(post.getEdited());
        postDto.setLikesCount(post.getLikes().size());
        if (hasLikedUsers) {
            List<UserDto> likedUsers = new ArrayList<>();
            post.getLikes().forEach(user -> likedUsers.add(UserDto.fromUser(user, false, false)));
            postDto.setLikedUsers(likedUsers);
        }
        return postDto;
    }

    @Override
    public int compareTo(PostDto other) {
        return Long.compare(getTime(), other.getTime());
    }
}
