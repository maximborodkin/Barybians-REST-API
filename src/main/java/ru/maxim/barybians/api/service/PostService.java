package ru.maxim.barybians.api.service;

import ru.maxim.barybians.api.model.Post;
import ru.maxim.barybians.api.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> findById(Long id);

    List<Post> findByUserId(Long id);

    Post addPost(Post post);

    Post editPost(Post post);

    boolean deletePost(Long id);

    boolean addLike(long postId, long userId);

    boolean removeLike(long postId, long userId);
}
