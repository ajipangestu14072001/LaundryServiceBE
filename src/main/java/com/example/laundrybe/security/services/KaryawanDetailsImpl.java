package com.example.laundrybe.security.services;

import com.example.laundrybe.models.Karyawan;
import com.example.laundrybe.models.Konsumen;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class KaryawanDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    private final String email;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final String nama;


    public KaryawanDetailsImpl(Long id, String username, String email, String password,
                               Collection<? extends GrantedAuthority> authorities, String nama) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.nama = nama;
    }

    public static KaryawanDetailsImpl build(Karyawan user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new KaryawanDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getNama());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        KaryawanDetailsImpl user = (KaryawanDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}

