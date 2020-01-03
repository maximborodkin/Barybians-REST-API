package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.barybians.api.model.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long id);
}
