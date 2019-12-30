package ru.maxim.barybians.api.service;

import ru.maxim.barybians.api.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAll();

    Optional<Role> findById(Long id);

    Role findByName(String name);
}
