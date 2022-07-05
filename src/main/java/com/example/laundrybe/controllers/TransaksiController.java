package com.example.laundrybe.controllers;

import com.example.laundrybe.models.Karyawan;
import com.example.laundrybe.models.Transaksi;
import com.example.laundrybe.payload.ResponseHandler;
import com.example.laundrybe.repository.TransaksiRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {
    private final ResponseHandler responseHandler = new ResponseHandler();

    final
    TransaksiRepository transaksiRepository;

    public TransaksiController(TransaksiRepository transaksiRepository) {
        this.transaksiRepository = transaksiRepository;
    }

    @PostMapping("/laundry")
    public ResponseEntity<?> create(
            @RequestBody Transaksi customer) {
        try {
            Transaksi member = new Transaksi();
            member.setIdTransaksi(UUID.randomUUID().toString());
            member.setKaryawan(customer.getKaryawan());
            member.setKonsumen(customer.getKonsumen());
            member.setTanggalMasuk(customer.getTanggalMasuk());
            member.setTanggalKeluar(customer.getTanggalKeluar());
            member.setStatus(customer.getStatus());
            member.setJumlahBarang(customer.getJumlahBarang());
            member.setTanggalAmbil(customer.getTanggalAmbil());
            member.setTotalHarga(customer.getTotalHarga());
            transaksiRepository.save(member);
            List<Object> objects = new ArrayList<>();
            objects.add(member);
            return responseHandler.generateResponse("Transaksi Berhasil", HttpStatus.OK, objects);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/laundry")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "idTransaksi",
                    required = false,
                    defaultValue = "") String idTransaksi) {
        try {
            List<Transaksi> members;
            if (StringUtils.hasText(idTransaksi)) {
                members = transaksiRepository.findByIdTransaksiContaining(idTransaksi);
            } else {
                members = transaksiRepository.findAll();
            }

            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return responseHandler.generateResponse("Lihat Transaksi Berhasil", HttpStatus.OK, members);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
