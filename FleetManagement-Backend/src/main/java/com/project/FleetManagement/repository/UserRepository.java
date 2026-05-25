package com.project.FleetManagement.repository;

import com.project.FleetManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByusername(String username);
    boolean existsByusername(String username);
}
