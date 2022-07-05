package com.example.laundrybe.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "detil")
public class Detil {
    @Id
    @Column(name = "id_detil")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetil;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_transaksi", nullable = false)
    private Transaksi idTrans;
    @Column(name = "jumlah")
    private Integer jumlah;
    @Column(name = "harga")
    private Integer harga;

}
