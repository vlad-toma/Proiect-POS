package com.example.pos.entity;

import com.example.pos.dto.ProfesoriDTO;
import com.example.pos.enums.GradDidactic;
import com.example.pos.enums.TipAsociere;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profesori")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profesori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private String prenume;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private GradDidactic gradDidactic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipAsociere tipAsociere;

    private String afiliere;

    public Profesori(ProfesoriDTO profesoriDTO) {
        this.id = profesoriDTO.getId();
        this.nume = profesoriDTO.getNume();
        this.prenume = profesoriDTO.getPrenume();
        this.email = profesoriDTO.getEmail();
        this.gradDidactic = GradDidactic.valueOf(profesoriDTO.getGradDidactic());
        this.tipAsociere = TipAsociere.valueOf(profesoriDTO.getTipAsociere());
        this.afiliere = profesoriDTO.getAfiliere();
    }
}
