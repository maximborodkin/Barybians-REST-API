package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxim.barybians.api.dto.PostDto;
import ru.maxim.barybians.api.dto.PostRequestDto;
import ru.maxim.barybians.api.model.Post;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtTokenProvider;
import ru.maxim.barybians.api.service.PostService;
import ru.maxim.barybians.api.service.UserService;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/")
public class PostRestControllerV1 {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    // Get post by id
    @GetMapping(value = "posts/{id}")
    public ResponseEntity getPostById(@PathVariable(name = "id") Long id){
        if (postService.findById(id).isPresent()){
            PostDto postDto = PostDto.fromPost(postService.findById(id).get(), true, true, true, true, true);
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create post
    @PostMapping(value = "posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addPost(@RequestBody PostRequestDto postRequest,
                                  @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        String title = postRequest.getTitle();
        String text = postRequest.getText();
        Date time = new Date();
        if (text == null || text.isEmpty() || time.after(new Date())){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setText(text);
        post.setTime(time);
        post.setEdited(0);
        Post editedPost = postService.addPost(post);
        if (editedPost == null){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(PostDto.fromPost(editedPost, false, false, false, false, false), HttpStatus.OK);
        }
    }

    // Edit post by id
    @PutMapping(value = "posts/{id}")
    public ResponseEntity addPost(@PathVariable(name = "id") Long id,
                                  @RequestBody PostRequestDto postRequest,
                                  @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (!postService.findById(id).isPresent()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        String title = postRequest.getTitle();
        String text = postRequest.getText();
        Date time = new Date();
        if (text == null || text.isEmpty() || time.after(new Date())){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        Post post = new Post();
        post.setId(id);
        post.setUser(user);
        post.setTitle(title);
        post.setText(text);
        post.setTime(time);
        Post editedPost = postService.editPost(post);
        if (editedPost == null){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(PostDto.fromPost(editedPost, false, false, false, false, false), HttpStatus.OK);
        }
    }

    // Delete post by id
    @DeleteMapping(value = "posts/{id}")
    public ResponseEntity addPost(@PathVariable(name = "id") Long id,
                                  @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (!postService.findById(id).isPresent()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        if (postService.deletePost(id)){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unable to delete post", HttpStatus.BAD_REQUEST);
        }
    }

    // Add like to post
    @PostMapping(value = "posts/{id}/likes")
    public ResponseEntity addLike(@PathVariable(name = "id") Long id, @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (!postService.findById(id).isPresent()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        if (postService.findById(id).get().getLikes().contains(user)){
            return new ResponseEntity<>("Like already present", HttpStatus.BAD_REQUEST);
        }
        if (postService.addLike(id, user.getId())){
            return new ResponseEntity<>("Like added", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Like not added", HttpStatus.BAD_REQUEST);
        }
    }

    // Remove like from post
    @DeleteMapping(value = "posts/{id}/likes")
    public ResponseEntity removeLike(@PathVariable(name = "id") Long id, @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (!postService.findById(id).isPresent()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        if (!postService.findById(id).get().getLikes().contains(user)){
            return new ResponseEntity<>("Like is not present", HttpStatus.BAD_REQUEST);
        }
        if (postService.removeLike(id, user.getId())){
            return new ResponseEntity<>("Like removed", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Like not removed", HttpStatus.BAD_REQUEST);
        }
    }
}