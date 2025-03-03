package com.example.pos_mongo.entity;

import com.example.pos_mongo.dto.CatalogDisciplinaDTO;

public class GenericMapper {
    public static CatalogDisciplinaDTO toDisciplinaDTO(CatalogDisciplina catalogDisciplina) {
        return new CatalogDisciplinaDTO(
                catalogDisciplina.getId(),
                catalogDisciplina.getDenumireProgramStudii(),
                catalogDisciplina.getAnDeStudiu(),
                catalogDisciplina.getNumeTitular(),
                catalogDisciplina.getGradDidactic(),
                catalogDisciplina.getStudenti()
        );
    }
}
