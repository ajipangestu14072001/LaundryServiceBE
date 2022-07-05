package com.example.laundrybe.controllers;

import com.example.laundrybe.models.Layanan;
import com.example.laundrybe.payload.ResponseHandler;
import com.example.laundrybe.repository.LayananRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/layanan")
public class LayananController {
    private final ResponseHandler responseHandler = new ResponseHandler();
    final
    LayananRepository layananRepository;

    public LayananController(LayananRepository layananRepository) {
        this.layananRepository = layananRepository;
    }

    @PostMapping("/laundry")
    public ResponseEntity<?> create(
            @RequestBody Layanan customer) {
        try {
            Layanan member = new Layanan();
            member.setHarga(customer.getHarga());
            member.setNamaLayanan(customer.getNamaLayanan());
            member.setIdDetil(customer.getIdDetil());
            layananRepository.save(member);
            List<Object> objects = new ArrayList<>();
            objects.add(member);
            return responseHandler.generateResponse("Tambah Layanan Berhasil", HttpStatus.OK, objects);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/laundry")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "namaLayanan",
                    required = false,
                    defaultValue = "") String namaLayanan) {
        try {
            List<Layanan> members;
            if (StringUtils.hasText(namaLayanan)) {
                members = layananRepository.findByNamaLayananContaining(namaLayanan);
            } else {
                members = layananRepository.findAll();
            }

            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return responseHandler.generateResponse("Lihat Layanan Berhasil", HttpStatus.OK, members);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
