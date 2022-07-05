package com.example.laundrybe.repository;

import com.example.laundrybe.models.Jenis;
import com.example.laundrybe.models.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JenisRepository extends JpaRepository<Jenis, Long> {
    List<Jenis> findByNamaJenisContaining(String namaJenis);
}
