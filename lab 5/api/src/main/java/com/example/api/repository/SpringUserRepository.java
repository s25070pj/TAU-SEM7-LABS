package com.example.api.repository;

import com.example.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpringUserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User>, UserRepository {
}
