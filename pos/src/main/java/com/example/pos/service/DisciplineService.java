package com.example.pos.service;

import com.example.pos.dto.DisciplineDTO;

import com.example.pos.entity.Discipline;
import com.example.pos.entity.GenericMapper;

import com.example.pos.enums.*;
import com.example.pos.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<DisciplineDTO> getAllDisciplines() {
        List<Discipline> disciplines = disciplineRepository.findAll();
        return disciplines.stream()
                .map(GenericMapper::toDisciplineDTO)
                .collect(Collectors.toList());
    }

    public DisciplineDTO getDisciplineByCod(String cod) {
        Optional<Discipline> discipline = disciplineRepository.findByCod(cod);
        return discipline.map(GenericMapper::toDisciplineDTO).orElse(null);
    }

    public List<DisciplineDTO> getDisciplineByidTitular(Integer id) {
        List<Discipline> disciplines = disciplineRepository.findByIdTitular(id);
        return disciplines.stream()
                .map(GenericMapper::toDisciplineDTO)
                .collect(Collectors.toList());
    }

    public DisciplineDTO createDisciplina(DisciplineDTO disciplineDTO) throws Exception {
        if (disciplineRepository.existsByCod(disciplineDTO.getCod())) {
            throw new Exception("Date invalide");
        }

        if (disciplineDTO.getCod() == null || disciplineDTO.getIdTitular() == null || disciplineDTO.getNumeDisciplina() == null
         || disciplineDTO.getAnStudiu() == null || disciplineDTO.getTipDisciplina() == null || disciplineDTO.getCategorieDisciplina() == null
         || disciplineDTO.getTipExaminare() == null) {
            throw new Exception("Date invalide");
        }

        if (! (disciplineDTO.getTipDisciplina().equals(TipDisciplina.impusa.toString()) ||
                disciplineDTO.getTipDisciplina().equals(TipDisciplina.optionala.toString()) ||
                disciplineDTO.getTipDisciplina().equals(TipDisciplina.liber_aleasa.toString()) )) {
            throw new Exception("Date invalide");
        }

        if (! (disciplineDTO.getCategorieDisciplina().equals(CategorieDisciplina.domeniu.toString()) ||
                disciplineDTO.getCategorieDisciplina().equals(CategorieDisciplina.specialitate.toString()) ||
                disciplineDTO.getCategorieDisciplina().equals(CategorieDisciplina.adiacenta.toString()) )) {
            throw new Exception("Date invalide");
        }

        if (! (disciplineDTO.getTipExaminare().equals(TipExaminare.examen.toString()) ||
                disciplineDTO.getTipExaminare().equals(TipExaminare.colocviu.toString()) )) {
            throw new Exception("Date invalide");
        }

        Discipline discipline = new Discipline(disciplineDTO);
        discipline = disciplineRepository.save(discipline);
        return GenericMapper.toDisciplineDTO(discipline);
    }

    public DisciplineDTO updateDisciplina(String cod, DisciplineDTO disciplineDTO) throws Exception {
        Discipline existingDisciplina = disciplineRepository.findByCod(cod).orElse(null);

        if (existingDisciplina == null) {
            disciplineDTO.setCod(cod);
            return this.createDisciplina(disciplineDTO);
        }
        else {
            if (disciplineDTO.getIdTitular() == null || disciplineDTO.getNumeDisciplina() == null
                    || disciplineDTO.getAnStudiu() == null || disciplineDTO.getTipDisciplina() == null || disciplineDTO.getCategorieDisciplina() == null
                    || disciplineDTO.getTipExaminare() == null) {
                throw new Exception("Unprocessable Content");
            }
            existingDisciplina.setCod(disciplineDTO.getCod());
            existingDisciplina.setNumeDisciplina(disciplineDTO.getNumeDisciplina());
            existingDisciplina.setIdTitular(disciplineDTO.getIdTitular());
            existingDisciplina.setTipDisciplina(TipDisciplina.valueOf(disciplineDTO.getTipDisciplina()));
            existingDisciplina.setAnStudiu(disciplineDTO.getAnStudiu());
            existingDisciplina.setCategorieDisciplina(CategorieDisciplina.valueOf(disciplineDTO.getCategorieDisciplina()));
            existingDisciplina.setTipExaminare(TipExaminare.valueOf(disciplineDTO.getTipExaminare()));

            return this.createDisciplina(GenericMapper.toDisciplineDTO(existingDisciplina));
        }
    }

    public DisciplineDTO patchDisciplina(String cod, DisciplineDTO disciplineDTO) throws Exception {
        Discipline existingDisciplina = disciplineRepository.findByCod(cod).orElse(null);

        if (existingDisciplina == null) {
            throw new Exception("Not Found");
        }
        else {
            if (disciplineDTO.getCod() != null)
                existingDisciplina.setCod(disciplineDTO.getCod());
            if (disciplineDTO.getNumeDisciplina() != null)
                existingDisciplina.setNumeDisciplina(disciplineDTO.getNumeDisciplina());
            if (disciplineDTO.getIdTitular() != null)
                existingDisciplina.setIdTitular(disciplineDTO.getIdTitular());
            if (disciplineDTO.getTipDisciplina() != null)
                existingDisciplina.setTipDisciplina(TipDisciplina.valueOf(disciplineDTO.getTipDisciplina()));
            if (disciplineDTO.getAnStudiu() != null)
                existingDisciplina.setAnStudiu(disciplineDTO.getAnStudiu());
            if (disciplineDTO.getCategorieDisciplina() != null)
                existingDisciplina.setCategorieDisciplina(CategorieDisciplina.valueOf(disciplineDTO.getCategorieDisciplina()));
            if (disciplineDTO.getTipExaminare() != null)
                existingDisciplina.setTipExaminare(TipExaminare.valueOf(disciplineDTO.getTipExaminare()));

            return this.createDisciplina(GenericMapper.toDisciplineDTO(existingDisciplina));
        }
    }

    public void deleteDisciplineByCod(String cod) throws Exception {
        if (!disciplineRepository.existsByCod(cod)) {
            throw new Exception("Resursa nu exista.");
        }

        disciplineRepository.deleteByCod(cod);
    }
}
