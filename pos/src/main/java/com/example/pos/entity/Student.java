package com.example.pos.entity;

import com.example.pos.dto.StudentDTO;
import com.example.pos.enums.CicluStudii;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "studenti")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private String prenume;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CicluStudii cicluStudii;

    @Column(nullable = false)
    private int anStudiu;

    @Column(nullable = false)
    private int grupa;

    public Student(StudentDTO studentDTO) {
        this.id = studentDTO.getId();
        this.nume = studentDTO.getNume();
        this.prenume = studentDTO.getPrenume();
        this.email = studentDTO.getEmail();
        this.cicluStudii = CicluStudii.valueOf(studentDTO.getCicluStudii());
        this.anStudiu = studentDTO.getAnStudiu();
        this.grupa = studentDTO.getGrupa();
    }
}
