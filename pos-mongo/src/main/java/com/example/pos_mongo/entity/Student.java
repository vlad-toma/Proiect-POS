package com.example.pos_mongo.entity;

import ch.qos.logback.core.joran.sanity.Pair;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private ObjectId id;
    private String prenume;
    private String nume;
    private Stare stare;
    private String idCatalogPromovat; // Optional
    private List<Nota> note;         // Vector de tip <proba, nota>
    private Double medie;            // Optional


    public enum Stare {
        PROMOVAT,
        RESPINS,
        ECHIVALAT,
        ABSENT
    }
}
