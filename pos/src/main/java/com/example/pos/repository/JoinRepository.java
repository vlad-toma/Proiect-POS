package com.example.pos.repository;

import com.example.pos.entity.Join;
import com.example.pos.entity.JoinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRepository extends JpaRepository<Join, Integer> {
    List<Join> findByStudentId(int id);
    List<Join> findByDisciplinaId(String disciplinaId);

    void deleteAllByStudentId(int id);
    void deleteAllByDisciplinaId(String id);
}
