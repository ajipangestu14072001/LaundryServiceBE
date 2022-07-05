package com.example.laundrybe.repository;

import com.example.laundrybe.models.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    List<Transaksi> findByIdTransaksiContaining(String idTransaksi);
}
