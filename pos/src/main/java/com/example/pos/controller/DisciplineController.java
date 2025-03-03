package com.example.pos.controller;

import com.example.pos.dto.DisciplineDTO;

import com.example.pos.entity.Discipline;
import com.example.pos.jwt.jwtValidate;
import com.example.pos.repository.DisciplineRepository;
import com.example.pos.service.DisciplineService;

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
@RequestMapping("/api/academia/discipline")
@CrossOrigin(origins = "http://localhost:5173")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;
    @Autowired
    private DisciplineRepository disciplineRepository;

    @GetMapping
    public ResponseEntity<?> getAllDisciplines(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        List<EntityModel<DisciplineDTO>> disciplineModels = disciplineService.getAllDisciplines().stream()
                .map(discipline -> EntityModel.of(discipline,
                        linkTo(methodOn(DisciplineController.class).getDisciplineByCod(discipline.getCod(), authorizationHeader)).withSelfRel(),
                        linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withRel("disciplines")))
                .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(disciplineModels,
                linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/{cod}")
    public ResponseEntity<?> getDisciplineByCod(@PathVariable String cod, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        DisciplineDTO disciplina = disciplineService.getDisciplineByCod(cod);
        if (disciplina == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<DisciplineDTO> disciplineModel = EntityModel.of(disciplina,
                linkTo(methodOn(DisciplineController.class).getDisciplineByCod(cod, authorizationHeader)).withSelfRel(),
                linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withRel("disciplines"));

        return ResponseEntity.ok(disciplineModel);
    }

    @PostMapping
    public ResponseEntity<?> createDisciplina(@RequestBody DisciplineDTO disciplinaDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
            DisciplineDTO newDisciplina = disciplineService.createDisciplina(disciplinaDTO);
            EntityModel<DisciplineDTO> disciplineModel = EntityModel.of(newDisciplina,
                    linkTo(methodOn(DisciplineController.class).getDisciplineByCod(newDisciplina.getCod(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withRel("disciplines"));

            return new ResponseEntity<>(disciplineModel, HttpStatus.CREATED);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: codul a fost deja folosit." -> new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" -> new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PutMapping("/{cod}")
    public ResponseEntity<?> updateDisciplina(@PathVariable String cod, @RequestBody DisciplineDTO disciplineDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
            Boolean exists = disciplineRepository.existsByCod(cod);
            DisciplineDTO updatedDisciplina = disciplineService.updateDisciplina(cod, disciplineDTO);

            EntityModel<DisciplineDTO> disciplineModel = EntityModel.of(updatedDisciplina,
                    linkTo(methodOn(DisciplineController.class).getDisciplineByCod(updatedDisciplina.getCod(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(DisciplineController.class).getAllDisciplines(authorizationHeader)).withRel("disciplines"));

            return new ResponseEntity<>(disciplineModel, exists ? HttpStatus.NO_CONTENT : HttpStatus.CREATED);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Conflict de unicitate: codul a fost deja folosit." -> new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
                case "Date invalide" -> new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                case "Not Found" -> new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                case "Unprocessable Content" -> new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @DeleteMapping("/{cod}")
    public ResponseEntity<?> deleteDisciplinaByCod(@PathVariable String cod, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
            disciplineService.deleteDisciplineByCod(cod);
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
