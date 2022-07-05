package com.example.laundrybe.repository;

import com.example.laundrybe.models.Konsumen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KonsumenRepository extends JpaRepository<Konsumen, Long> {

    Optional<Konsumen> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}