package com.example.pos.dto;

import com.example.pos.entity.Profesori;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfesoriDTO {

    private Integer id;
    private String nume;
    private String prenume;
    private String email;
    private String gradDidactic;
    private String tipAsociere;
    private String afiliere;

    public ProfesoriDTO(Profesori profesor) {
        this.id = profesor.getId();
        this.nume = profesor.getNume();
        this.prenume = profesor.getPrenume();
        this.email = profesor.getEmail();
        this.gradDidactic = profesor.getGradDidactic() != null ? profesor.getGradDidactic().name() : null;
        this.tipAsociere = profesor.getTipAsociere().name();
        this.afiliere = profesor.getAfiliere();
    }
}
