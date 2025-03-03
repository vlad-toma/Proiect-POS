package com.example.pos.entity;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.enums.CategorieDisciplina;
import com.example.pos.enums.TipDisciplina;
import com.example.pos.enums.TipExaminare;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {

    @Id
    private String cod;

    @Column(nullable = false)
    private String numeDisciplina;

    @JoinColumn(name = "ID_titular", referencedColumnName = "id")
    private Integer idTitular;

    @Column(nullable = false)
    private int anStudiu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipDisciplina tipDisciplina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategorieDisciplina categorieDisciplina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipExaminare tipExaminare;

    public Discipline(DisciplineDTO disciplineDTO) {
        this.cod = disciplineDTO.getCod();
        this.numeDisciplina = disciplineDTO.getNumeDisciplina();
        this.anStudiu = disciplineDTO.getAnStudiu();
        this.tipDisciplina = TipDisciplina.valueOf(disciplineDTO.getTipDisciplina());
        this.categorieDisciplina = CategorieDisciplina.valueOf(disciplineDTO.getCategorieDisciplina());
        this.tipExaminare = TipExaminare.valueOf(disciplineDTO.getTipExaminare());
    }
}
