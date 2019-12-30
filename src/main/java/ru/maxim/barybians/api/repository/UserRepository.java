package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.barybians.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
