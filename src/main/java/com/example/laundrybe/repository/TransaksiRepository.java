package com.example.laundrybe.repository;

import com.example.laundrybe.models.Karyawan;
import com.example.laundrybe.models.Konsumen;
import com.example.laundrybe.models.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
    List<Transaksi> findByIdTransaksiContaining(String idTransaksi);
}
