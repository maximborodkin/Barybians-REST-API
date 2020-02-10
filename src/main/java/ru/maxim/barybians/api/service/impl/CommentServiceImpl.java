package ru.maxim.barybians.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxim.barybians.api.model.Comment;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.repository.CommentRepository;
import ru.maxim.barybians.api.repository.UserRepository;
import ru.maxim.barybians.api.service.CommentService;
import java.util.*;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public Comment addComment(Comment comment) {
        User user = comment.getAuthor();
        user.setLastVisit(new Date());
        userRepository.save(user);
        return commentRepository.save(comment);
    }

    @Override
    public Comment editComment(Comment comment) {
        User user = comment.getAuthor();
        user.setLastVisit(new Date());
        userRepository.save(user);
        return commentRepository.save(comment);
    }

    @Override
    public boolean deleteComment(long id) {
        if (commentRepository.findById(id).isPresent()){
            User user = commentRepository.findById(id).get().getAuthor();
            user.setLastVisit(new Date());
            userRepository.save(user);
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}