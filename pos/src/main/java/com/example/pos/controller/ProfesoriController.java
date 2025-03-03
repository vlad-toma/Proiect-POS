package com.example.pos.controller;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.dto.JoinDTO;
import com.example.pos.dto.ProfesoriDTO;

import com.example.pos.jwt.jwtValidate;
import com.example.pos.service.ProfesoriService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/academia/profesori")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfesoriController {

    @Autowired
    private ProfesoriService profesoriService;

    @GetMapping
    public ResponseEntity<?> getAllProfesori(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        List<EntityModel<ProfesoriDTO>> profesoriModels = profesoriService.getAllProfesori().stream()
                .map(profesor -> EntityModel.of(profesor,
                        linkTo(methodOn(ProfesoriController.class).getProfesorById(profesor.getId(), authorizationHeader)).withSelfRel(),
                        linkTo(methodOn(ProfesoriController.class).getAllProfesori(authorizationHeader)).withRel("profesori")))
                .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(profesoriModels,
                linkTo(methodOn(ProfesoriController.class).getAllProfesori(authorizationHeader)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfesorById(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        ProfesoriDTO profesor = profesoriService.getProfesorById(id);
        if (profesor == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<ProfesoriDTO> profesorModel = EntityModel.of(profesor,
                linkTo(methodOn(ProfesoriController.class).getProfesorById(id, authorizationHeader)).withSelfRel(),
                linkTo(methodOn(ProfesoriController.class).getAllProfesori(authorizationHeader)).withRel("profesori"));

        return ResponseEntity.ok(profesorModel);
    }

    @GetMapping("/{email}/discipline")
    public ResponseEntity<?> getProfesorByIdDiscipline(@PathVariable String email, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        ProfesoriDTO profesor = profesoriService.getProfesorByEmail(email);
        if (profesor == null) {
            return ResponseEntity.notFound().build();
        }

        List<DisciplineDTO> discipline = profesoriService.getProfesorByIdDiscipline(email);
        if (discipline.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DisciplineDTO>> disciplineModel = discipline.stream().
                map(disciplina -> EntityModel.of(disciplina,
                linkTo(methodOn(DisciplineController.class).getDisciplineByCod(disciplina.getCod(), authorizationHeader)).withSelfRel(),
                linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withRel("discipline")))
                .toList();

        CollectionModel<EntityModel<DisciplineDTO>> collectionModel = CollectionModel.of(disciplineModel,
                linkTo(methodOn(ProfesoriController.class).getProfesorById(profesor.getId(), authorizationHeader)).withSelfRel(),
                linkTo(methodOn(ProfesoriController.class).getAllProfesori(authorizationHeader)).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<?> createProfesor(@RequestBody ProfesoriDTO profesorDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
            ProfesoriDTO newProfesor = profesoriService.createProfesor(profesorDTO);
            EntityModel<ProfesoriDTO> profesorModel = EntityModel.of(newProfesor,
                    linkTo(methodOn(ProfesoriController.class).getProfesorById(newProfesor.getId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(ProfesoriController.class).getAllProfesori(authorizationHeader)).withRel("profesori"));

            return new ResponseEntity<>(profesorModel, HttpStatus.CREATED);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: emailul a fost deja folosit." -> new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" -> new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfesor(@PathVariable Integer id, @RequestBody ProfesoriDTO profesorDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
            ProfesoriDTO updatedProfesor = profesoriService.updateProfesor(id, profesorDTO);

            EntityModel<ProfesoriDTO> profesorModel = EntityModel.of(updatedProfesor,
                    linkTo(methodOn(ProfesoriController.class).getProfesorById(updatedProfesor.getId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(ProfesoriController.class).getAllProfesori(authorizationHeader)).withRel("profesori"));

            return new ResponseEntity<>(profesorModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: emailul a fost deja folosit." -> new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" -> new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                case "Not Found" -> new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                case "Unprocessable Content" -> new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesorById(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
            profesoriService.deleteProfesorById(id);
            return ResponseEntity.noContent().build();
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Resursa nu exista." -> new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }
}
