package com.example.laundrybe.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "transaksi")
public class Transaksi {
    @Id
    @Column(name = "id_transaksi")
    private String idTransaksi;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_konsumen", nullable = false)
    private Konsumen konsumen;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_karyawan", nullable = false)
    private Karyawan karyawan;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_masuk")
    private Date tanggalMasuk;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_keluar")
    private Date tanggalKeluar;
    @Column(name = "status")
    private String status;
    @Column(name = "jumlah_barang")
    private Integer jumlahBarang;
    @Column(name = "total_harga")
    private Integer totalHarga;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_ambil")
    private Date tanggalAmbil;

}
