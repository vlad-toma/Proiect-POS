package com.example.pos.dto;

import com.example.pos.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    private String nume;
    private String prenume;
    private String email;
    private String cicluStudii;
    private Integer anStudiu;
    private Integer grupa;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.nume = student.getNume();
        this.prenume = student.getPrenume();
        this.email = student.getEmail();
        this.cicluStudii = student.getCicluStudii().name();
        this.anStudiu = student.getAnStudiu();
        this.grupa = student.getGrupa();
    }
}
