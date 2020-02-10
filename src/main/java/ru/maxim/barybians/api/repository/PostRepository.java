package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.maxim.barybians.api.model.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("FROM Post WHERE user_id = :id")
    List<Post> findByUserId(Long id);
}
