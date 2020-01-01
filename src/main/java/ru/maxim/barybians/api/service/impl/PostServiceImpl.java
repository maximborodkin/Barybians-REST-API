package ru.maxim.barybians.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxim.barybians.api.model.Post;
import ru.maxim.barybians.api.repository.PostRepository;
import ru.maxim.barybians.api.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findByUserId(Long id) {
        return postRepository.findByUserId(id);
    }
}
