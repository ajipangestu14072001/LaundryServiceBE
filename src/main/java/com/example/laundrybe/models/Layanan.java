package com.example.laundrybe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "layanan")
public class Layanan {
    @Id
    @Column(name = "id_layanan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLayanan;
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name="id_detil", nullable = false)
//    private Detil idDetil;
    @Column(name = "nama_layanan")
    private String namaLayanan;
    @Column(name = "harga")
    private Float harga;
    @Column(name = "kategori")
    private String kategori;

}
