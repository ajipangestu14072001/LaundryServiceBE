package com.example.laundrybe.controllers;

import com.example.laundrybe.models.Detil;
import com.example.laundrybe.models.Jenis;
import com.example.laundrybe.payload.ResponseHandler;
import com.example.laundrybe.repository.DetilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/detil")
public class DetilController {
    private final ResponseHandler responseHandler = new ResponseHandler();

    final
    DetilRepository detilRepository;

    public DetilController(DetilRepository detilRepository) {
        this.detilRepository = detilRepository;
    }

    @PostMapping("/laundry")
    public ResponseEntity<?> create(
            @RequestBody Detil customer) {
        try {
            Detil member = new Detil();
            member.setHarga(customer.getHarga());
            member.setJumlah(customer.getJumlah());
            detilRepository.save(member);
            List<Object> objects = new ArrayList<>();
            objects.add(member);
            return responseHandler.generateResponse("Tambah Jenis Berhasil", HttpStatus.OK, objects);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/laundry")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "idTrans",
                    required = false,
                    defaultValue = "") String idTrans) {
        try {
            List<Detil> members;
            if (StringUtils.hasText(idTrans)) {
                members = detilRepository.findByidDetilContaining(idTrans);
            } else {
                members = detilRepository.findAll();
            }

            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return responseHandler.generateResponse("Lihat Detil Berhasil", HttpStatus.OK, members);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
