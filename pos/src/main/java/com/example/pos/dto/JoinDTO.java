package com.example.pos.dto;

import com.example.pos.entity.Join;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {
    private int studentId;

    private String disciplinaId;

    public JoinDTO(Join join) {
        this.studentId = join.getStudentId();
        this.disciplinaId = join.getDisciplinaId();
    }
}
