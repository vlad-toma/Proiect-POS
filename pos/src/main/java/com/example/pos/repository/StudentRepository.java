package com.example.pos.repository;

import com.example.pos.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAll();

    Boolean existsByEmail(String email);
    Student findByEmail(String email);
}
