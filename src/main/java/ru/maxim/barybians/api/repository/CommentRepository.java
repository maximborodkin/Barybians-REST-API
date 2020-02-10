package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maxim.barybians.api.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // В качестве имени таблицы указывается имя model-класса, который описывает эту сущность
    @Query("FROM Comment WHERE post_id = :id")
    List<Comment> findByPostId(Long id);
}
