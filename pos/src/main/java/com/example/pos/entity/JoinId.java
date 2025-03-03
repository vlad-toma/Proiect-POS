package com.example.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

public class JoinId implements Serializable {

    @Id
    @Column(name = "StudentiID")
    private int studentId;

    @Id
    @Column(name = "DisciplinaID")
    private String disciplinaId;

    // Constructori, getter, setter, hashCode și equals
    public JoinId() {}

    public JoinId(int studentId, String disciplinaId) {
        this.studentId = studentId;
        this.disciplinaId = disciplinaId;
    }

    // Gettere și settere
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(String disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    // Implementare hashCode și equals pentru compararea cheii primare
    @Override
    public int hashCode() {
        return 31 * studentId + (disciplinaId != null ? disciplinaId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        JoinId joinId = (JoinId) obj;

        if (studentId != joinId.studentId) return false;
        return disciplinaId != null ? disciplinaId.equals(joinId.disciplinaId) : joinId.disciplinaId == null;
    }
}

