package com.example.laundrybe.repository;

import com.example.laundrybe.models.Karyawan;
import com.example.laundrybe.models.Konsumen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan, Long> {
    Optional<Karyawan> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
