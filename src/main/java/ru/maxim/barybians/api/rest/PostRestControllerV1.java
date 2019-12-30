package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim.barybians.api.dto.PostDto;
import ru.maxim.barybians.api.service.PostService;

@RestController
@RequestMapping(value = "/api/v1/")
public class PostRestControllerV1 {

    @Autowired
    PostService postService;

    @GetMapping(value = "posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return new ResponseEntity(PostDto.fromPost(postService.findById(id).get(), true, true), HttpStatus.OK);
    }
}
