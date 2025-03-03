package com.example.pos.controller;

import com.example.pos.dto.JoinDTO;
import com.example.pos.dto.StudentDTO;
import com.example.pos.jwt.jwtValidate;
import com.example.pos.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/academia/join")
@CrossOrigin(origins = "http://localhost:5173")
public class JoinController {
    @Autowired
    private JoinService joinService;

    @GetMapping
    public ResponseEntity<?> getJoin(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            List<JoinDTO> joins = joinService.getAllJoin();
            if (joins.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<EntityModel<JoinDTO>> joinModels = joins.stream()
                    .map(join -> EntityModel.of(join,
                            linkTo(methodOn(JoinController.class).getJoinSid(join.getStudentId(), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(JoinController.class).getJoinDid(join.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins")))
                    .toList();

            CollectionModel<EntityModel<JoinDTO>> collectionModel = CollectionModel.of(joinModels,
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getJoinSid(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            List<JoinDTO> joins = joinService.getJoinSid(id);
            if (joins.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<EntityModel<JoinDTO>> joinModels = joins.stream()
                    .map(join -> EntityModel.of(join,
                            linkTo(methodOn(JoinController.class).getJoinSid(join.getStudentId(), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(JoinController.class).getJoinDid(join.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins")))
                    .toList();

            CollectionModel<EntityModel<JoinDTO>> collectionModel = CollectionModel.of(joinModels,
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/disciplina/{id}")
    public ResponseEntity<?> getJoinDid(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            List<JoinDTO> joins = joinService.getJoinDid(id);
            if (joins.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<EntityModel<JoinDTO>> joinModels = joins.stream()
                    .map(join -> EntityModel.of(join,
                            linkTo(methodOn(JoinController.class).getJoinSid(join.getStudentId(), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(JoinController.class).getJoinDid(join.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                            linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins")))
                    .toList();

            CollectionModel<EntityModel<JoinDTO>> collectionModel = CollectionModel.of(joinModels,
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping
    public ResponseEntity<?> createJoin(@RequestBody JoinDTO joinDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            JoinDTO newJoin = joinService.createJoin(joinDTO);
            EntityModel<JoinDTO> joinModel = EntityModel.of(newJoin,
                    linkTo(methodOn(JoinController.class).getJoinSid(newJoin.getStudentId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoinDid(newJoin.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins"));

            return new ResponseEntity<>(joinModel, HttpStatus.CREATED);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "400" ->
                        new ResponseEntity<>("StudentID sau DisciplinaID gresite", HttpStatus.BAD_REQUEST);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateJoinS(@PathVariable int id, @RequestBody String cod, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            JoinDTO updatedJoin = joinService.updateJoinS(id, cod);
            EntityModel<JoinDTO> joinModel = EntityModel.of(updatedJoin,
                    linkTo(methodOn(JoinController.class).getJoinSid(updatedJoin.getStudentId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoinDid(updatedJoin.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins"));

            return new ResponseEntity<>(joinModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "400" ->
                        new ResponseEntity<>("StudentID sau DisciplinaID gresite", HttpStatus.BAD_REQUEST);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PutMapping("/disciplina/{cod}")
    public ResponseEntity<?> updateJoinD(@PathVariable String cod, @RequestBody int id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            JoinDTO updatedJoin = joinService.updateJoinD(cod, id);
            EntityModel<JoinDTO> joinModel = EntityModel.of(updatedJoin,
                    linkTo(methodOn(JoinController.class).getJoinSid(updatedJoin.getStudentId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoinDid(updatedJoin.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins"));

            return new ResponseEntity<>(joinModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "400" ->
                        new ResponseEntity<>("StudentID sau DisciplinaID gresite", HttpStatus.BAD_REQUEST);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PatchMapping("/student/{id}")
    public ResponseEntity<?> patchJoinS(@PathVariable int id, @RequestBody String cod, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            JoinDTO updatedJoin = joinService.patchJoinS(id, cod);
            EntityModel<JoinDTO> joinModel = EntityModel.of(updatedJoin,
                    linkTo(methodOn(JoinController.class).getJoinSid(updatedJoin.getStudentId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoinDid(updatedJoin.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins"));

            return new ResponseEntity<>(joinModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "400" ->
                        new ResponseEntity<>("StudentID sau DisciplinaID gresite", HttpStatus.BAD_REQUEST);
                case "Not Found" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PatchMapping("/disciplina/{cod}")
    public ResponseEntity<?> patchJoinD(@PathVariable String cod, @RequestBody int id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            JoinDTO updatedJoin = joinService.patchJoinD(cod, id);
            EntityModel<JoinDTO> joinModel = EntityModel.of(updatedJoin,
                    linkTo(methodOn(JoinController.class).getJoinSid(updatedJoin.getStudentId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoinDid(updatedJoin.getDisciplinaId(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(JoinController.class).getJoin(authorizationHeader)).withRel("joins"));

            return new ResponseEntity<>(joinModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "400" ->
                        new ResponseEntity<>("StudentID sau DisciplinaID gresite", HttpStatus.BAD_REQUEST);
                case "Not Found" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteJoinS(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            joinService.deleteJoinS(id);

            return ResponseEntity.noContent().build();
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Not Found" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @DeleteMapping("/disciplina/{id}")
    public ResponseEntity<?> deleteJoinD(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            joinService.deleteJoinD(id);
            return ResponseEntity.noContent().build();
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "Not Found" ->
                        new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                case "500" ->
                        new ResponseEntity<>("Stergere nereusita", HttpStatus.INTERNAL_SERVER_ERROR);
                default -> new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }
}
