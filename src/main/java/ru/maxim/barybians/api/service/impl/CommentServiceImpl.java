package ru.maxim.barybians.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxim.barybians.api.model.Comment;
import ru.maxim.barybians.api.repository.CommentRepository;
import ru.maxim.barybians.api.service.CommentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findCommentsByPostId(Long id) {
        List<Comment> allComments = commentRepository.findAll();
        List<Comment> postComments = new ArrayList<>();
        allComments.forEach(comment -> {
            if (comment.getPost().getId() == id){
                postComments.add(comment);
            }
        });
        Collections.reverse(postComments);
        return postComments;
    }
}