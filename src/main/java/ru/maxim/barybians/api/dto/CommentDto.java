package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.maxim.barybians.api.model.Comment;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CommentDto {

    private long id;
    private PostDto post;
    private UserDto author;
    private String text;
    private long time;

    public Comment toComment(){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setPost(post.toPost());
        comment.setAuthor(author.toUser());
        comment.setText(text);
        comment.setTime(new Date(time));
        return comment;
    }

    public static CommentDto fromComment(Comment comment, boolean hasUser, boolean hasPost){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        if (hasPost) {
            commentDto.setPost(PostDto.fromPost(comment.getPost(), false, false, false, hasUser, hasPost));
        }
        if (hasUser){
            commentDto.setAuthor(UserDto.fromUser(comment.getAuthor(), false, false, false));
        }
        commentDto.setText(comment.getText());
        commentDto.setTime(comment.getTime().getTime());
        return commentDto;
    }
}