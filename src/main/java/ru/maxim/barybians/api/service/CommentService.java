package ru.maxim.barybians.api.service;

import ru.maxim.barybians.api.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);

    List<Comment> findCommentsByPostId(Long id);

    Comment addComment(Comment comment);

    Comment editComment(Comment comment);

    boolean deleteComment(long id);
}
