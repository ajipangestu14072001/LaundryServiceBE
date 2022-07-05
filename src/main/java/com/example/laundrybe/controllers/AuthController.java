package com.example.laundrybe.controllers;

import com.example.laundrybe.models.*;
import com.example.laundrybe.payload.*;
import com.example.laundrybe.repository.KaryawanRepository;
import com.example.laundrybe.repository.KonsumenRepository;
import com.example.laundrybe.repository.RoleKaryawanRepository;
import com.example.laundrybe.repository.RoleKosumenRepository;
import com.example.laundrybe.security.jwt.JwtUtils;
import com.example.laundrybe.security.services.KaryawanDetailsImpl;
import com.example.laundrybe.security.services.KonsumenDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final
    RoleKaryawanRepository roleKaryawanRepository;

    final
    KaryawanRepository karyawanRepository;

    final
    AuthenticationManager authenticationManager2;

    final
    AuthenticationManager authenticationManager;

    final
    KonsumenRepository konsumenRepository;

    final
    RoleKosumenRepository roleKosumenRepository;

    final
    PasswordEncoder encoder;

    final
    JwtUtils jwtUtils;

    private final ResponseHandler responseHandler = new ResponseHandler();


    public AuthController(AuthenticationManager authenticationManager, KonsumenRepository konsumenRepository, RoleKosumenRepository roleKosumenRepository, PasswordEncoder encoder, JwtUtils jwtUtils, KaryawanRepository karyawanRepository, RoleKaryawanRepository roleKaryawanRepository, AuthenticationManager authenticationManager2) {
        this.authenticationManager = authenticationManager;
        this.konsumenRepository = konsumenRepository;
        this.roleKosumenRepository = roleKosumenRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.karyawanRepository = karyawanRepository;
        this.roleKaryawanRepository = roleKaryawanRepository;
        this.authenticationManager2 = authenticationManager2;
    }

    @PostMapping("/konsumen/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (konsumenRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User sudah digunakan"));
        }

        if (konsumenRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email sudah digunakan"));
        }

        try {
            Konsumen user = new Konsumen(signupRequest.getNama(), signupRequest.getEmail(),
                    signupRequest.getTelepon(), signupRequest.getUsername(),
                    encoder.encode(signupRequest.getPassword()));

            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleKosumenRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleKosumenRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                            roles.add(adminRole);

                            break;
                        case "mod":
                            Role modRole = roleKosumenRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                            roles.add(modRole);

                            break;
                        default:
                            Role userRole = roleKosumenRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            konsumenRepository.save(user);
            List<Object> objects = new ArrayList<>();
            objects.add(user);
            return responseHandler.generateResponse("Register Berhasil", HttpStatus.OK, objects);
        } catch (Exception e) {
            return responseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "[]");
        }
    }

    @PostMapping("/karyawan/signup")
    public ResponseEntity<?> registerKaryawan(@Valid @RequestBody SignupKaryawanRequest signupRequest) {
        if (karyawanRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User sudah digunakan"));
        }

        if (karyawanRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email sudah digunakan"));
        }

        try {
            Karyawan user = new Karyawan(signupRequest.getNama(), signupRequest.getEmail(),
                    signupRequest.getUsername(),
                    encoder.encode(signupRequest.getPassword()));

            Set<String> strRoles = signupRequest.getRole();
            Set<KaryawanRole> roles = new HashSet<>();

            if (strRoles == null) {
                KaryawanRole userRole = roleKaryawanRepository.findByName(EKaryawan.ROLE_ADMINISTRASI)
                        .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    if ("mod".equals(role)) {
                        KaryawanRole adminRole = roleKaryawanRepository.findByName(EKaryawan.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                        roles.add(adminRole);
                    } else {
                        KaryawanRole userRole = roleKaryawanRepository.findByName(EKaryawan.ROLE_ADMINISTRASI)
                                .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                        roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            karyawanRepository.save(user);
            List<Object> objects = new ArrayList<>();
            objects.add(user);
            return responseHandler.generateResponse("Register Berhasil", HttpStatus.OK, objects);
        } catch (Exception e) {
            return responseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "[]");
        }
    }

    @PostMapping("/konsumen/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            KonsumenDetailsImpl userDetails = (KonsumenDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtToken(authentication);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            JwtResponse jwtResponse = new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles,
                    userDetails.getNama()
            );
            List<Object> objects = new ArrayList<>();
            objects.add(jwtResponse);
            return responseHandler.generateResponse("OK", HttpStatus.OK,objects);
        } catch (Exception e) {
            return responseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "[]");
        }

    }

    @PostMapping("/karyawan/signin")
    public ResponseEntity<?> authenticateUserKaryawan(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            KaryawanDetailsImpl userDetails = (KaryawanDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtTokenKaryawan(authentication);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponse jwtResponse = new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles,
                    userDetails.getNama()
            );
            List<Object> objects = new ArrayList<>();
            objects.add(jwtResponse);
            return responseHandler.generateResponse("OK", HttpStatus.OK,objects);
        } catch (Exception e) {
            return responseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "[]");
        }

    }
}
