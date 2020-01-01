package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim.barybians.api.dto.CommentDto;
import ru.maxim.barybians.api.model.Comment;
import ru.maxim.barybians.api.service.CommentService;

@RestController
@RequestMapping(value = "/api/v1/")
public class CommentRestController {

    @Autowired
    CommentService commentService;

    @GetMapping(value = "comments/{id}")
    public ResponseEntity getCommentById(@PathVariable(name = "id") Long id){
        if (commentService.findById(id).isPresent()){
            Comment comment = commentService.findById(id).get();
            return new ResponseEntity<>(CommentDto.fromComment(comment, true, true), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }
}
