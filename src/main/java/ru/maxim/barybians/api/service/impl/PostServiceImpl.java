package ru.maxim.barybians.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxim.barybians.api.model.Post;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.repository.PostRepository;
import ru.maxim.barybians.api.repository.UserRepository;
import ru.maxim.barybians.api.service.PostService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findByUserId(Long id) {
        return postRepository.findByUserId(id);
    }

    @Override
    public Post addPost(Post post) {
        User user = post.getUser();
        user.setLastVisit(new Date());
        userRepository.save(user);
        return postRepository.save(post);
    }

    @Override
    public Post editPost(Post post) {
        post.setEdited(1);
        User user = post.getUser();
        user.setLastVisit(new Date());
        userRepository.save(user);
        return postRepository.save(post);
    }

    @Override
    public boolean deletePost(Long id) {
        if (postRepository.findById(id).isPresent()){
            User user = postRepository.findById(id).get().getUser();
            user.setLastVisit(new Date());
            userRepository.save(user);
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean addLike(long postId, long userId) {
        if (postRepository.findById(postId).isPresent() && userRepository.findById(userId).isPresent()){
            Post post = postRepository.findById(postId).get();
            User user = userRepository.findById(userId).get();
            user.setLastVisit(new Date());
            userRepository.save(user);
            if (post.getLikes().contains(user)) return false;
            post.getLikes().add(user);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeLike(long postId, long userId) {
        if (postRepository.findById(postId).isPresent() && userRepository.findById(userId).isPresent()){
            Post post = postRepository.findById(postId).get();
            User user = userRepository.findById(userId).get();
            user.setLastVisit(new Date());
            userRepository.save(user);
            if (!post.getLikes().contains(user)) return false;
            post.getLikes().remove(user);
            postRepository.save(post);
            return true;
        }
        return false;
    }
}