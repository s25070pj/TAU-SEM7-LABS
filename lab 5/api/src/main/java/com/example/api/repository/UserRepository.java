package com.example.api.repository;

import com.example.api.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);

    List<User> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
