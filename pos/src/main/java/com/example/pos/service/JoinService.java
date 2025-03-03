package com.example.pos.service;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.dto.JoinDTO;
import com.example.pos.dto.StudentDTO;
import com.example.pos.entity.GenericMapper;
import com.example.pos.entity.Join;
import com.example.pos.entity.Student;
import com.example.pos.enums.CicluStudii;
import com.example.pos.repository.JoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoinService {
    @Autowired
    private JoinRepository joinRepository;

    public List<JoinDTO> getAllJoin() {
        return joinRepository.findAll().stream()
                .map(GenericMapper::toJoinDTO)
                .collect(Collectors.toList());
    }

    public List<JoinDTO> getJoinSid(int id) {
        return joinRepository.findByStudentId(id).stream()
                .map(GenericMapper::toJoinDTO)
                .collect(Collectors.toList());
    }

    public List<JoinDTO> getJoinDid(String id) {
        return joinRepository.findByDisciplinaId(id).stream()
                .map(GenericMapper::toJoinDTO)
                .collect(Collectors.toList());
    }

    public boolean existJoinSidDid(int id, String cod) {
        List<JoinDTO> list = joinRepository.findAll().stream()
                .map(GenericMapper::toJoinDTO)
                .collect(Collectors.toList());
        list = list.stream().filter(join -> join.getDisciplinaId().equals(cod)).toList();
        return !list.isEmpty();
    }

    public JoinDTO createJoin(JoinDTO joinDTO) throws Exception{
        StudentService studentService = new StudentService();
        StudentDTO studentDTO = studentService.getStudentById(joinDTO.getStudentId());
        if (studentDTO == null) {
            throw new Exception("400");
        }
        DisciplineService disciplineService = new DisciplineService();
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineByCod(joinDTO.getDisciplinaId());
        if (disciplineDTO == null) {
            throw new Exception("400");
        }

        Join join = new Join(joinDTO);
        join = joinRepository.save(join);

        return GenericMapper.toJoinDTO(join);
    }

    public JoinDTO updateJoinS(int id, String cod) throws Exception {
        if (!this.existJoinSidDid(id, cod)) {
            throw new Exception("Not Found");
        }
        else {
            StudentService studentService = new StudentService();
            StudentDTO studentDTO = studentService.getStudentById(id);
            if (studentDTO == null) {
                throw new Exception("400");
            }
            DisciplineService disciplineService = new DisciplineService();
            DisciplineDTO disciplineDTO = disciplineService.getDisciplineByCod(cod);
            if (disciplineDTO == null) {
                throw new Exception("400");
            }
            Join newJoin = new Join(id, cod);

            return this.createJoin(GenericMapper.toJoinDTO(newJoin));
        }
    }

    public JoinDTO updateJoinD(String cod, int id) throws Exception {
        if (!this.existJoinSidDid(id, cod)) {
            throw new Exception("Not Found");
        }
        else {
            StudentService studentService = new StudentService();
            StudentDTO studentDTO = studentService.getStudentById(id);
            if (studentDTO == null) {
                throw new Exception("400");
            }
            DisciplineService disciplineService = new DisciplineService();
            DisciplineDTO disciplineDTO = disciplineService.getDisciplineByCod(cod);
            if (disciplineDTO == null) {
                throw new Exception("400");
            }
            Join newJoin = new Join(id, cod);

            return this.createJoin(GenericMapper.toJoinDTO(newJoin));
        }
    }


    public JoinDTO patchJoinS(int id, String cod) throws Exception {
        if (!this.existJoinSidDid(id, cod)) {
            throw new Exception("Not Found");
        }
        else {
            StudentService studentService = new StudentService();
            StudentDTO studentDTO = studentService.getStudentById(id);
            if (studentDTO == null) {
                throw new Exception("400");
            }
            DisciplineService disciplineService = new DisciplineService();
            DisciplineDTO disciplineDTO = disciplineService.getDisciplineByCod(cod);
            if (disciplineDTO == null) {
                throw new Exception("400");
            }
            Join newJoin = new Join(id, cod);

            return this.createJoin(GenericMapper.toJoinDTO(newJoin));
        }
    }

    public JoinDTO patchJoinD(String cod, int id) throws Exception {
        if (!this.existJoinSidDid(id, cod)) {
            throw new Exception("Not Found");
        }
        else {
            StudentService studentService = new StudentService();
            StudentDTO studentDTO = studentService.getStudentById(id);
            if (studentDTO == null) {
                throw new Exception("400");
            }
            DisciplineService disciplineService = new DisciplineService();
            DisciplineDTO disciplineDTO = disciplineService.getDisciplineByCod(cod);
            if (disciplineDTO == null) {
                throw new Exception("400");
            }
            Join newJoin = new Join(id, cod);

            return this.createJoin(GenericMapper.toJoinDTO(newJoin));
        }
    }

    public void deleteJoinS(int id) throws Exception {
        if (this.getJoinSid(id).isEmpty()) {
            throw new Exception("Not Found");
        }
        joinRepository.deleteAllByStudentId(id);
        if (!this.getJoinSid(id).isEmpty()) {
            throw new Exception("500");
        }
    }

    public void deleteJoinD(String id) throws Exception {
        if (this.getJoinDid(id).isEmpty()) {
            throw new Exception("Not Found");
        }
        joinRepository.deleteAllByDisciplinaId(id);
        if (!this.getJoinDid(id).isEmpty()) {
            throw new Exception("500");
        }
    }
}
