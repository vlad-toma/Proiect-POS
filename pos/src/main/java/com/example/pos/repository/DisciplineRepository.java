package com.example.pos.repository;

import com.example.pos.entity.Discipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, String> {
    Page<Discipline> findAll(Pageable pageable);

    Optional<Discipline> findByCod(String cod);

    Boolean existsByCod(String cod);

    Optional<Discipline> findByNumeDisciplina(String denumire);

    void deleteByCod(String cod);

    List<Discipline> findByIdTitular(Integer idTitular);
}
