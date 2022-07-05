package com.example.laundrybe.security.services;

import com.example.laundrybe.models.Konsumen;
import com.example.laundrybe.repository.KonsumenRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KonsumenService implements UserDetailsService {
    final
    KonsumenRepository konsumenRepository;

    public KonsumenService(KonsumenRepository konsumenRepository) {
        this.konsumenRepository = konsumenRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Konsumen user = konsumenRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return KonsumenDetailsImpl.build(user);
    }

}
