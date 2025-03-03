package com.example.pos.service;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.dto.JoinDTO;
import com.example.pos.dto.StudentDTO;
import com.example.pos.entity.Discipline;
import com.example.pos.entity.GenericMapper;
import com.example.pos.entity.Join;
import com.example.pos.entity.Student;
import com.example.pos.enums.CicluStudii;
import com.example.pos.repository.DisciplineRepository;
import com.example.pos.repository.JoinRepository;
import com.example.pos.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private JoinRepository joinRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(GenericMapper::toStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Integer id) {
        return studentRepository.findById(id)
                .map(GenericMapper::toStudentDTO)
                .orElse(null);
    }

    public StudentDTO getStudentByEmail(String email) {
        if (studentRepository.findByEmail(email) == null)
            return null;
        return GenericMapper.toStudentDTO(studentRepository.findByEmail(email));
    }

    public List<DisciplineDTO> getStudentByIdDiscipline(String email) {

        Student student = studentRepository.findByEmail(email);
        List<Join> listDisciplineID = joinRepository.findByStudentId(Math.toIntExact(student.getId()));
        List<Discipline> listDiscipline = (List<Discipline>) listDisciplineID.stream()
                .map(dis -> disciplineRepository.findByCod(dis.getDisciplinaId()));

        return listDiscipline.stream().map(GenericMapper::toDisciplineDTO).collect(Collectors.toList());
    }

    public StudentDTO createStudent(StudentDTO studentDTO) throws Exception {
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            if (studentRepository.findByEmail(studentDTO.getEmail()).getId() != studentDTO.getId()) {
                throw new Exception("Conflict de unicitate: adresa de email a fost deja folosita.");
            }
        }

        if (studentDTO.getNume() == null || studentDTO.getPrenume() == null || studentDTO.getCicluStudii() == null) {
            throw new Exception("Date invalide");
        }

        if ( (! (studentDTO.getCicluStudii().equals(CicluStudii.licenta.toString()) && (
                (studentDTO.getGrupa() >= 1001 && studentDTO.getGrupa() <= 1012) ||
                (studentDTO.getGrupa() >= 1101 && studentDTO.getGrupa() <= 1112) ||
                (studentDTO.getGrupa() >= 1201 && studentDTO.getGrupa() <= 1212) ||
                (studentDTO.getGrupa() >= 1301 && studentDTO.getGrupa() <= 1312) )))
            && (! (studentDTO.getCicluStudii().equals(CicluStudii.master.toString()) && (
                (studentDTO.getGrupa() >= 2001 && studentDTO.getGrupa() <= 2004) ||
                (studentDTO.getGrupa() >= 2101 && studentDTO.getGrupa() <= 2104)))) ) {
            throw new Exception("Date invalide");
        }

        if ( (studentDTO.getCicluStudii().equals(CicluStudii.licenta.toString()) && (studentDTO.getAnStudiu() < 1 || studentDTO.getAnStudiu() > 4))
                || (studentDTO.getCicluStudii().equals(CicluStudii.master.toString()) && (studentDTO.getAnStudiu() < 1 || studentDTO.getAnStudiu() > 2)) ) {
            throw new Exception("Date invalide");
        }

        String[] email1 = studentDTO.getEmail().split("@");
        if (email1.length != 2) {
            throw new Exception("Date invalide");
        }
        String[] email2 = email1[1].split("\\.");
        if (email2.length != 2) {
            throw new Exception("Date invalide");
        }

        Student student = new Student(studentDTO);
        student = studentRepository.save(student);

        return GenericMapper.toStudentDTO(student);
    }

    public void deleteStudentById(Integer id) throws Exception {
        if (!studentRepository.existsById(id)) {
            throw new Exception("Resursa nu exista.");
        }
        studentRepository.deleteById(id);
    }

    public StudentDTO updateStudent(Integer id, StudentDTO studentDTO) throws Exception {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            throw new Exception("Not Found");
        }
        else {
            if (studentDTO.getNume() == null || studentDTO.getPrenume() == null || studentDTO.getCicluStudii() == null
                    || studentDTO.getAnStudiu() == null || studentDTO.getGrupa() == null || studentDTO.getEmail() == null) {
                throw new Exception("Unprocessable Content");
            }
            existingStudent.setNume(studentDTO.getNume());
            existingStudent.setPrenume(studentDTO.getPrenume());
            existingStudent.setEmail(studentDTO.getEmail());
            existingStudent.setCicluStudii(CicluStudii.valueOf(studentDTO.getCicluStudii()));
            existingStudent.setAnStudiu(studentDTO.getAnStudiu());
            existingStudent.setGrupa(studentDTO.getGrupa());
            existingStudent.setId(Long.valueOf(id));

            return this.createStudent(GenericMapper.toStudentDTO(existingStudent));
        }
    }

    public StudentDTO patchStudent(Integer id, StudentDTO studentDTO) throws Exception {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            throw new Exception("Not Found");
        }
        else {
            if (studentDTO.getNume() != null)
                existingStudent.setNume(studentDTO.getNume());
            if (studentDTO.getPrenume() != null)
                existingStudent.setPrenume(studentDTO.getPrenume());
            if (studentDTO.getEmail() != null)
                existingStudent.setEmail(studentDTO.getEmail());
            if (studentDTO.getCicluStudii() != null)
                existingStudent.setCicluStudii(CicluStudii.valueOf(studentDTO.getCicluStudii()));
            if (studentDTO.getAnStudiu() != null)
                existingStudent.setAnStudiu(studentDTO.getAnStudiu());
            if (studentDTO.getGrupa() != null)
                existingStudent.setGrupa(studentDTO.getGrupa());
            existingStudent.setId(Long.valueOf(id));

            return this.createStudent(GenericMapper.toStudentDTO(existingStudent));
        }
    }
}
