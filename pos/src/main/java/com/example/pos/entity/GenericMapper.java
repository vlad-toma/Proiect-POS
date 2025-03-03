package com.example.pos.entity;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.dto.JoinDTO;
import com.example.pos.dto.ProfesoriDTO;
import com.example.pos.dto.StudentDTO;

public class GenericMapper {

    public static StudentDTO toStudentDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getNume(),
                student.getPrenume(),
                student.getEmail(),
                student.getCicluStudii().name(),
                student.getAnStudiu(),
                student.getGrupa()
        );
    }

    public static ProfesoriDTO toProfesoriDTO(Profesori profesor) {
        return new ProfesoriDTO(
                profesor.getId(),
                profesor.getNume(),
                profesor.getPrenume(),
                profesor.getEmail(),
                profesor.getGradDidactic() != null ? profesor.getGradDidactic().name() : null,
                profesor.getTipAsociere().name(),
                profesor.getAfiliere()
        );
    }

    public static DisciplineDTO toDisciplineDTO(Discipline disciplina) {
        return new DisciplineDTO(
                disciplina.getCod(),
                disciplina.getNumeDisciplina(),
                disciplina.getIdTitular(),
                disciplina.getAnStudiu(),
                disciplina.getTipDisciplina().name(),
                disciplina.getCategorieDisciplina().name(),
                disciplina.getTipExaminare().name()
        );
    }

    public static JoinDTO toJoinDTO(Join join) {
        return new JoinDTO(
                join.getStudentId(),
                join.getDisciplinaId()
        );
    }
}
