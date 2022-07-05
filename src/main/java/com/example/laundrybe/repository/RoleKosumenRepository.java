package com.example.laundrybe.repository;

import com.example.laundrybe.models.ERole;
import com.example.laundrybe.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleKosumenRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
