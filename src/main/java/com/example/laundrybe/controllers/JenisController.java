package com.example.laundrybe.controllers;

import com.example.laundrybe.models.Jenis;
import com.example.laundrybe.payload.ResponseHandler;
import com.example.laundrybe.repository.JenisRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/jenis")
public class JenisController {
    private final ResponseHandler responseHandler = new ResponseHandler();

    final
    JenisRepository jenisRepository;

    public JenisController(JenisRepository jenisRepository) {
        this.jenisRepository = jenisRepository;
    }

    @PostMapping("/laundry")
    public ResponseEntity<?> create(
            @RequestBody Jenis customer) {
        try {
            Jenis member = new Jenis();
            member.setNamaJenis(customer.getNamaJenis());
            member.setIdLayanan(customer.getIdLayanan());
            member.setKategori(customer.getKategori());
            jenisRepository.save(member);
            List<Object> objects = new ArrayList<>();
            objects.add(member);
            return responseHandler.generateResponse("Tambah Jenis Berhasil", HttpStatus.OK, objects);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/laundry")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "namaJenis",
                    required = false,
                    defaultValue = "") String namaJenis) {
        try {
            List<Jenis> members;
            if (StringUtils.hasText(namaJenis)) {
                members = jenisRepository.findByNamaJenisContaining(namaJenis);
            } else {
                members = jenisRepository.findAll();
            }

            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return responseHandler.generateResponse("Lihat Jenis Berhasil", HttpStatus.OK, members);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
