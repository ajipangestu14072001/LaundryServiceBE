package com.example.laundrybe.security.services;

import com.example.laundrybe.models.Karyawan;
import com.example.laundrybe.models.Konsumen;
import com.example.laundrybe.repository.KaryawanRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KaryawanService implements UserDetailsService {
    final
    KaryawanRepository karyawanRepository;

    public KaryawanService(KaryawanRepository karyawanRepository) {
        this.karyawanRepository = karyawanRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Karyawan user = karyawanRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return KaryawanDetailsImpl.build(user);
    }

}
