package com.example.pos_mongo.entity;

import com.example.pos_mongo.dto.CatalogDisciplinaDTO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "catalog_disciplina")
public class CatalogDisciplina {
    private ObjectId id;
    private String denumireProgramStudii;
    private int anDeStudiu;
    private String numeTitular;
    private String gradDidactic; // Optional
    private List<Student> studenti;

    public CatalogDisciplina(CatalogDisciplinaDTO catalogDisciplinaDTO) {
        this.id = catalogDisciplinaDTO.getId();
        this.denumireProgramStudii = catalogDisciplinaDTO.getDenumireProgramStudii();
        this.anDeStudiu = catalogDisciplinaDTO.getAnDeStudiu();
        this.numeTitular = catalogDisciplinaDTO.getNumeTitular();
        this.gradDidactic = catalogDisciplinaDTO.getGradDidactic();
        this.studenti = catalogDisciplinaDTO.getStudenti();
    }
}
