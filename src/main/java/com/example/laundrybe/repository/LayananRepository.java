package com.example.laundrybe.repository;

import com.example.laundrybe.models.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LayananRepository extends JpaRepository<Layanan, Long> {
    List<Layanan> findByNamaLayananContaining(String namaLayanan);
    List<Layanan> findByKategoriContaining(String kategori);


}
