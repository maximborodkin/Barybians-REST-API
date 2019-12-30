package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.barybians.api.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
