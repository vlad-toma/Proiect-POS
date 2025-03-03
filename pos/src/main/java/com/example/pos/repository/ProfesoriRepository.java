package com.example.pos.repository;

import com.example.pos.entity.Profesori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfesoriRepository extends JpaRepository<Profesori, Integer> {
    List<Profesori> findAll();
    Optional<Profesori> findById(Integer id);
    Boolean existsByEmail(String email);
    Profesori findByEmail(String email);
}
