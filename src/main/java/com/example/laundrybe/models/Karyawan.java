package com.example.laundrybe.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "karyawan")
@NoArgsConstructor
public class Karyawan {
    @Id
    @Column(name = "id_karyawan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "karyawan_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<KaryawanRole> roles = new HashSet<>();

    public Karyawan(String nama,String email,String username, String password) {
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
