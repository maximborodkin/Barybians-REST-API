package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxim.barybians.api.dto.CommentDto;
import ru.maxim.barybians.api.dto.CommentRequestDto;
import ru.maxim.barybians.api.model.Comment;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtTokenProvider;
import ru.maxim.barybians.api.service.CommentService;
import ru.maxim.barybians.api.service.PostService;
import ru.maxim.barybians.api.service.UserService;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/")
public class CommentRestControllerV1 {

    private CommentService commentService;
    private PostService postService;
    private UserService userService;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public CommentRestControllerV1(CommentService commentService, PostService postService, UserService userService, JwtTokenProvider tokenProvider) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    // Get comment by id
    @GetMapping(value = "comments/{id}")
    public ResponseEntity getCommentById(@PathVariable(name = "id") Long id){
        if (commentService.findById(id).isPresent()){
            Comment comment = commentService.findById(id).get();
            return new ResponseEntity<>(CommentDto.fromComment(comment, true, true), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }

    // Create comment
    @PostMapping(value = "comments")
    public ResponseEntity addComment(@RequestBody CommentRequestDto commentRequest,
                                     @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        long postId = commentRequest.getPostId();
        String text = commentRequest.getText();
        Date time = new Date();
        if (!postService.findById(postId).isPresent()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        if (text == null || text.isEmpty() || time.after(new Date())){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        Comment comment = new Comment();
        comment.setPost(postService.findById(postId).get());
        comment.setAuthor(user);
        comment.setText(text);
        comment.setTime(time);
        Comment savedComment = commentService.addComment(comment);
        if (savedComment != null){
            return new ResponseEntity<>(CommentDto.fromComment(savedComment, false, false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Comment not saved", HttpStatus.BAD_REQUEST);
        }
    }

    // Edit comment
    @PutMapping(value = "comments/{id}")
    public ResponseEntity editComment(@PathVariable(name = "id") long commentId,
                                      @RequestBody CommentRequestDto commentRequest,
                                      @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        long postId = commentRequest.getPostId();
        String text = commentRequest.getText();
        Date time = new Date();
        if (!postService.findById(postId).isPresent()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        if (!commentService.findById(commentId).isPresent()){
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
        if (text == null || text.isEmpty() || time.after(new Date())){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        if (commentService.findById(commentId).get().getAuthor().getId() != user.getId()){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setPost(postService.findById(postId).get());
        comment.setAuthor(user);
        comment.setText(text);
        comment.setTime(time);
        Comment editedComment = commentService.addComment(comment);
        if (editedComment != null){
            return new ResponseEntity<>(CommentDto.fromComment(editedComment, false, false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Comment not edited", HttpStatus.BAD_REQUEST);
        }
    }

    // Delete comment
    @DeleteMapping(value = "comments/{id}")
    public ResponseEntity deleteComment(@PathVariable(name = "id") long commentId,
                                        @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (!commentService.findById(commentId).isPresent()){
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
        if (commentService.findById(commentId).get().getAuthor().getId() != user.getId()){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (commentService.deleteComment(commentId)){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Comment not deleted", HttpStatus.BAD_REQUEST);
        }
    }
}