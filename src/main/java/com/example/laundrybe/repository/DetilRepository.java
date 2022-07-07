package com.example.laundrybe.repository;

import com.example.laundrybe.models.Detil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetilRepository extends JpaRepository<Detil, Long> {
    List<Detil> findByidDetilContaining(String idDetil);

}
