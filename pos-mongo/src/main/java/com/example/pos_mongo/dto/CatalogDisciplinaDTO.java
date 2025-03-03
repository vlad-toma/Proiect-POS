package com.example.pos_mongo.dto;

import com.example.pos_mongo.entity.CatalogDisciplina;
import com.example.pos_mongo.entity.Student;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogDisciplinaDTO {
    private ObjectId id;
    private String denumireProgramStudii;
    private int anDeStudiu;
    private String numeTitular;
    private String gradDidactic;
    private List<Student> studenti;

    public CatalogDisciplinaDTO(CatalogDisciplina catalogDisciplina) {
        this.id = catalogDisciplina.getId();
        this.denumireProgramStudii = catalogDisciplina.getDenumireProgramStudii();
        this.anDeStudiu = catalogDisciplina.getAnDeStudiu();
        this.numeTitular = catalogDisciplina.getNumeTitular();
        this.gradDidactic = catalogDisciplina.getGradDidactic();
        this.studenti = catalogDisciplina.getStudenti();
    }
}
