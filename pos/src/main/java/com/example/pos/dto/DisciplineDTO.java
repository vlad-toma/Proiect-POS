package com.example.pos.dto;

import com.example.pos.entity.Discipline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineDTO {

    private String cod;
    private String numeDisciplina;
    private Integer idTitular;
    private Integer anStudiu;
    private String tipDisciplina;
    private String categorieDisciplina;
    private String tipExaminare;

    public DisciplineDTO(Discipline discipline) {
        this.cod = discipline.getCod();
        this.numeDisciplina = discipline.getNumeDisciplina();
        this.idTitular = discipline.getIdTitular();
        this.anStudiu = discipline.getAnStudiu();
        this.tipDisciplina = discipline.getTipDisciplina().name();
        this.categorieDisciplina = discipline.getCategorieDisciplina().name();
        this.tipExaminare = discipline.getTipExaminare().name();
    }
}
