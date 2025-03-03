package com.example.pos_mongo.service;

import com.example.pos_mongo.dto.CatalogDisciplinaDTO;
import com.example.pos_mongo.entity.CatalogDisciplina;
import com.example.pos_mongo.entity.GenericMapper;
import com.example.pos_mongo.repository.CatalogRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.catalog.CatalogException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public List<CatalogDisciplinaDTO> getAllCatalogs() {
        return catalogRepository.findAll().stream().
                map(GenericMapper::toDisciplinaDTO).
                collect(Collectors.toList());
    }

    public CatalogDisciplinaDTO getCatalogById(ObjectId id) throws Exception {
        if (!ObjectId.isValid(id.toString()))
            throw new Exception("Catalog ID is invalid");
        CatalogDisciplina catalog = catalogRepository.findById(id);
        return catalog != null ? GenericMapper.toDisciplinaDTO(catalog) : null;
    }

    public CatalogDisciplinaDTO createCatalog(CatalogDisciplinaDTO catalogDisciplinaDTO) throws Exception {
        /*if (!ObjectId.isValid(catalogDisciplina.getId().toString()))
            throw new Exception("Catalog ID is invalid");*/
        return GenericMapper.toDisciplinaDTO(catalogRepository.save(new CatalogDisciplina(catalogDisciplinaDTO)));
    }

    public CatalogDisciplinaDTO updateCatalog(ObjectId id, CatalogDisciplinaDTO catalogDisciplinaDTO) throws Exception{
        if (!ObjectId.isValid(id.toString()))
            throw new Exception("Catalog ID is invalid");

        CatalogDisciplina existingCatalog = catalogRepository.findById(id);

        if (existingCatalog == null) {
            existingCatalog = new CatalogDisciplina(catalogDisciplinaDTO);
        }
        existingCatalog.setId(id);
        return this.createCatalog(GenericMapper.toDisciplinaDTO(existingCatalog));
    }

    public CatalogDisciplinaDTO patchCatalog(ObjectId id, CatalogDisciplinaDTO catalogDTO) throws Exception {
        if (!ObjectId.isValid(id.toString()))
            throw new Exception("Catalog ID is invalid");

        CatalogDisciplina existingCatalog = catalogRepository.findById(id);

        if (existingCatalog == null) {
            throw new Exception("Not Found");
        }
        else {
            if (catalogDTO.getDenumireProgramStudii() != null)
                existingCatalog.setDenumireProgramStudii(catalogDTO.getDenumireProgramStudii());
            if (catalogDTO.getAnDeStudiu() != 0)
                existingCatalog.setAnDeStudiu(catalogDTO.getAnDeStudiu());
            if (catalogDTO.getNumeTitular() != null)
                existingCatalog.setNumeTitular(catalogDTO.getNumeTitular());
            if (catalogDTO.getGradDidactic() != null)
                existingCatalog.setGradDidactic(catalogDTO.getGradDidactic());
            if (catalogDTO.getStudenti() != null)
                existingCatalog.setStudenti(catalogDTO.getStudenti());
            existingCatalog.setId(id);

            return this.createCatalog(GenericMapper.toDisciplinaDTO(existingCatalog));
        }
    }

    public void deleteCatalog(ObjectId id) throws Exception {
        if (!ObjectId.isValid(id.toString()))
            throw new Exception("Catalog ID is invalid");

        if (!catalogRepository.existsById(id.toString())) {
            throw new Exception("Resursa nu exista.");
        }
        catalogRepository.deleteById(id.toString());
    }
}
