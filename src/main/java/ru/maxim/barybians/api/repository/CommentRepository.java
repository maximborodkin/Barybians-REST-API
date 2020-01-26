package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.barybians.api.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
