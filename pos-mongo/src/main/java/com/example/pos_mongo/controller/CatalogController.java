package com.example.pos_mongo.controller;

import com.example.pos_mongo.dto.CatalogDisciplinaDTO;
import com.example.pos_mongo.entity.CatalogDisciplina;
import com.example.pos_mongo.jwt.jwtValidate;
import com.example.pos_mongo.service.CatalogService;
import org.bson.types.ObjectId;
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
@RequestMapping("/api/academia/catalog")
@CrossOrigin(origins = "http://localhost:5173")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public ResponseEntity<?> getAllCatalogs(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        List<CatalogDisciplinaDTO> catalogs = catalogService.getAllCatalogs();
        if (catalogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<EntityModel<CatalogDisciplinaDTO>> catalogModels = catalogs.stream()
                .map(c -> EntityModel.of(c,
                        linkTo(methodOn(CatalogController.class).getCatalogById(c.getId().toString(), authorizationHeader)).withSelfRel(),
                        linkTo(methodOn(CatalogController.class).getAllCatalogs(authorizationHeader)).withRel("catalogs")))
                .toList();

        CollectionModel<EntityModel<CatalogDisciplinaDTO>> collectionModel = CollectionModel.of(catalogModels,
                linkTo(methodOn(CatalogController.class).getAllCatalogs(authorizationHeader)).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCatalogById(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            CatalogDisciplinaDTO catalog = catalogService.getCatalogById(new ObjectId(id));
            if (catalog == null) {
                return ResponseEntity.notFound().build();
            }

            EntityModel<CatalogDisciplinaDTO> catalogModel = EntityModel.of(catalog,
                    linkTo(methodOn(CatalogController.class).getCatalogById(id, authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(CatalogController.class).getAllCatalogs(authorizationHeader)).withRel("catalogs"));

            return new ResponseEntity<>(catalogModel, HttpStatus.OK);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PostMapping
    public ResponseEntity<?> createCatalog(@RequestBody CatalogDisciplinaDTO catalogDisciplinaDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            CatalogDisciplinaDTO newCatalog = catalogService.createCatalog(catalogDisciplinaDTO);
            EntityModel<CatalogDisciplinaDTO> catalogModel = EntityModel.of(newCatalog,
                    linkTo(methodOn(CatalogController.class).getCatalogById(newCatalog.getId().toString(), authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(CatalogController.class).getAllCatalogs(authorizationHeader)).withRel("catalogs"));

            return new ResponseEntity<>(catalogModel, HttpStatus.CREATED);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCatalog(@PathVariable String id, @RequestBody CatalogDisciplinaDTO catalogDisciplinaDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            CatalogDisciplinaDTO updatedCatalog = catalogService.updateCatalog(new ObjectId(id), catalogDisciplinaDTO);
            EntityModel<CatalogDisciplinaDTO> catalogModel = EntityModel.of(updatedCatalog,
                    linkTo(methodOn(CatalogController.class).getCatalogById(id, authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(CatalogController.class).getAllCatalogs(authorizationHeader)).withRel("catalogs"));

            return new ResponseEntity<>(catalogModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchCatalog(@PathVariable String id, @RequestBody CatalogDisciplinaDTO catalogDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            CatalogDisciplinaDTO updatedCatalog = catalogService.patchCatalog(new ObjectId(id), catalogDTO);
            EntityModel<CatalogDisciplinaDTO> studentModel = EntityModel.of(updatedCatalog,
                    linkTo(methodOn(CatalogController.class).getCatalogById(id, authorizationHeader)).withSelfRel(),
                    linkTo(methodOn(CatalogController.class).getAllCatalogs(authorizationHeader)).withRel("catalogs"));

            return new ResponseEntity<>(studentModel, HttpStatus.NO_CONTENT);
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCatalog(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            jwtValidate.validateToken(authorizationHeader);

            catalogService.deleteCatalog(new ObjectId(id));
            return ResponseEntity.noContent().build();
        } catch (jwtValidate.jwtException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }
}
