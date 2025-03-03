package com.example.pos.entity;

import com.example.pos.dto.JoinDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="join_ds")
@IdClass(Join.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Join {
    @Id
    @Column(name = "StudentiID", nullable = false)
    private int studentId;

    @Id
    @Column(name = "DisciplinaID", nullable = false)
    private String disciplinaId;

    public Join(JoinDTO joinDTO) {
        this.studentId = joinDTO.getStudentId();
        this.disciplinaId = joinDTO.getDisciplinaId();
    }
}
