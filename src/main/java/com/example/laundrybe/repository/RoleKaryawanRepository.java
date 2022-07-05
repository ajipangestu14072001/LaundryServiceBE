package com.example.laundrybe.repository;

import com.example.laundrybe.models.EKaryawan;
import com.example.laundrybe.models.KaryawanRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleKaryawanRepository extends JpaRepository<KaryawanRole, Integer> {
    Optional<KaryawanRole> findByName(EKaryawan name);
}
