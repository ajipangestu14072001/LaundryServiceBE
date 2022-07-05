package com.example.laundrybe.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "jenis")
public class Jenis {
    @Id
    @Column(name = "id_jenis")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJenis;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_layanan", nullable = false)
    private Layanan idLayanan;
    @Column(name = "nama_jenis")
    private String namaJenis;
    @Column(name = "kategori")
    private String kategori;
}
