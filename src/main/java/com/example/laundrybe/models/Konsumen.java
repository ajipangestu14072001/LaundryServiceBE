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
@Table(name = "konsumen")
@NoArgsConstructor
public class Konsumen {
    @Id
    @Column(name = "id_konsumen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "email")
    private String email;
    @Column(name = "no_telp")
    private String noTelp;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Konsumen(String nama,String email, String noTelp,String username, String password) {
        this.nama = nama;
        this.email = email;
        this.noTelp = noTelp;
        this.username = username;
        this.password = password;
    }

}
