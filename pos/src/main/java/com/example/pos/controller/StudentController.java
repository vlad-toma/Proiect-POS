package com.example.pos.controller;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.dto.ProfesoriDTO;
import com.example.pos.dto.StudentDTO;
import com.example.pos.jwt.jwtValidate;
import com.example.pos.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pos.jwt.jwtValidate.jwtException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/academia/studenti")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            List<StudentDTO> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<EntityModel<StudentDTO>> studentModels = students.stream()
                    .map(student -> EntityModel.of(student,
                            linkTo(methodOn(StudentController.class).getStudentById(Math.toIntExact(student.getId()), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withRel("students")))
                    .toList();

            CollectionModel<EntityModel<StudentDTO>> collectionModel = CollectionModel.of(studentModels,
                    linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            StudentDTO student = studentService.getStudentById(id);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }

            EntityModel<StudentDTO> studentModel = EntityModel.of(student,
                    linkTo(methodOn(StudentController.class).getStudentById(id, authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withRel("students"));

            return new ResponseEntity<>(studentModel, HttpStatus.OK);
        } catch (jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{email}/discipline")
    public ResponseEntity<?> getStudentByIdDiscipline(@PathVariable String email, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        StudentDTO student = studentService.getStudentByEmail(email);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        List<DisciplineDTO> discipline = studentService.getStudentByIdDiscipline(email);
        if (discipline.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DisciplineDTO>> disciplineModel = discipline.stream().
                map(disciplina -> EntityModel.of(disciplina,
                        linkTo(methodOn(DisciplineController.class).getDisciplineByCod(disciplina.getCod(), authorizationHeader)).withSelfRel(),
                        linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withRel("discipline")))
                .toList();

        CollectionModel<EntityModel<DisciplineDTO>> collectionModel = CollectionModel.of(disciplineModel,
                linkTo(methodOn(StudentController.class).getStudentById(Math.toIntExact(student.getId()), authorizationHeader)).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            StudentDTO newStudent = studentService.createStudent(studentDTO);
            EntityModel<StudentDTO> studentModel = EntityModel.of(newStudent,
                    linkTo(methodOn(StudentController.class).getStudentById(Math.toIntExact(newStudent.getId()), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withRel("students"));

            return new ResponseEntity<>(studentModel, HttpStatus.CREATED);
        } catch (jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: adresa de email a fost deja folosita." ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
            EntityModel<StudentDTO> studentModel = EntityModel.of(updatedStudent,
                    linkTo(methodOn(StudentController.class).getStudentById(id, authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withRel("students"));

            return new ResponseEntity<>(studentModel, HttpStatus.NO_CONTENT);
        } catch (jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: adresa de email a fost deja folosita." ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                case "Not Found" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                case "Unprocessable Content" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            StudentDTO updatedStudent = studentService.patchStudent(id, studentDTO);
            EntityModel<StudentDTO> studentModel = EntityModel.of(updatedStudent,
                    linkTo(methodOn(StudentController.class).getStudentById(id, authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(StudentController.class).getAllStudents(authorizationHeader)).withRel("students"));

            return new ResponseEntity<>(studentModel, HttpStatus.NO_CONTENT);
        } catch (jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: adresa de email a fost deja folosita." ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                case "Not Found" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                case "Unprocessable Content" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            studentService.deleteStudentById(id);
            return ResponseEntity.noContent().build();
        } catch (jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Resursa nu exista." ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }
}

