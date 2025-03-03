package com.example.pos.service;

import com.example.pos.dto.DisciplineDTO;
import com.example.pos.dto.ProfesoriDTO;

import com.example.pos.entity.GenericMapper;
import com.example.pos.entity.Profesori;

import com.example.pos.enums.GradDidactic;
import com.example.pos.enums.TipAsociere;
import com.example.pos.repository.DisciplineRepository;
import com.example.pos.repository.ProfesoriRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesoriService {

    @Autowired
    private ProfesoriRepository profesoriRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<ProfesoriDTO> getAllProfesori() {
        return profesoriRepository.findAll().stream()
                .map(GenericMapper::toProfesoriDTO)
                .collect(Collectors.toList());
    }

    public ProfesoriDTO getProfesorById(Integer id) {
        return profesoriRepository.findById(id)
                .map(GenericMapper::toProfesoriDTO)
                .orElse(null);
    }

    public ProfesoriDTO getProfesorByEmail(String email) {
        if (profesoriRepository.findByEmail(email) == null)
            return null;
        return GenericMapper.toProfesoriDTO(profesoriRepository.findByEmail(email));
    }

    public List<DisciplineDTO> getProfesorByIdDiscipline(String email) {
        DisciplineService disciplineService = new DisciplineService();
        return disciplineRepository.findByIdTitular(profesoriRepository.findByEmail(email).getId())
                .stream().map(GenericMapper::toDisciplineDTO)
                .collect(Collectors.toList());
    }

    public ProfesoriDTO createProfesor(ProfesoriDTO profesorDTO) throws Exception {
        if (profesoriRepository.existsByEmail(profesorDTO.getEmail())) {
            if (profesoriRepository.findByEmail(profesorDTO.getEmail()).getId() != profesorDTO.getId()) {
                throw new Exception("Conflict de unicitate: adresa de email a fost deja folosita.");
            }
        }

        if (profesorDTO.getNume() == null || profesorDTO.getPrenume() == null || profesorDTO.getTipAsociere() == null) {
            throw new Exception("Date invalide");
        }

        if (profesorDTO.getGradDidactic() != null) {
            if (! (profesorDTO.getGradDidactic().equals(GradDidactic.asist.toString()) ||
                    profesorDTO.getGradDidactic().equals(GradDidactic.sef_lucr.toString()) ||
                    profesorDTO.getGradDidactic().equals(GradDidactic.conf.toString()) ||
                    profesorDTO.getGradDidactic().equals(GradDidactic.prof.toString()) )) {
                throw new Exception("Date invalide");
            }
        }

        if (! (profesorDTO.getTipAsociere().equals(TipAsociere.titular.toString()) ||
                profesorDTO.getTipAsociere().equals(TipAsociere.asociat.toString()) ||
                profesorDTO.getTipAsociere().equals(TipAsociere.extern.toString()) )) {
            throw new Exception("Date invalide");
        }

        String[] email1 = profesorDTO.getEmail().split("@");
        if (email1.length != 2) {
            throw new Exception("Date invalide");
        }
        String[] email2 = email1[1].split("\\.");
        if (email2.length != 2) {
            throw new Exception("Date invalide");
        }

        Profesori profesor = new Profesori(profesorDTO);
        profesor = profesoriRepository.save(profesor);

        return GenericMapper.toProfesoriDTO(profesor);
    }

    public void deleteProfesorById(Integer id) throws Exception {
        if (!profesoriRepository.existsById(id)) {
            throw new Exception("Resursa nu exista.");
        }
        profesoriRepository.deleteById(id);
    }

    public ProfesoriDTO updateProfesor(Integer id, ProfesoriDTO profesorDTO) throws Exception {
        Profesori existingProfesor = profesoriRepository.findById(id).orElse(null);

        if (existingProfesor == null) {
            throw new Exception("Not Found");
        }
        else {
            if (profesorDTO.getNume() == null || profesorDTO.getPrenume() == null || profesorDTO.getTipAsociere() == null
                     || profesorDTO.getEmail() == null) {
                throw new Exception("Unprocessable Content");
            }
            existingProfesor.setNume(profesorDTO.getNume());
            existingProfesor.setPrenume(profesorDTO.getPrenume());
            existingProfesor.setEmail(profesorDTO.getEmail());
            if (profesorDTO.getGradDidactic() != null)
                existingProfesor.setGradDidactic(GradDidactic.valueOf(profesorDTO.getGradDidactic()));
            existingProfesor.setTipAsociere(TipAsociere.valueOf(profesorDTO.getTipAsociere()));
            if (profesorDTO.getAfiliere() != null)
                existingProfesor.setAfiliere(profesorDTO.getAfiliere());
            existingProfesor.setId(id);

            return this.createProfesor(GenericMapper.toProfesoriDTO(existingProfesor));
        }
    }

    public ProfesoriDTO patchProfesor(Integer id, ProfesoriDTO profesorDTO) throws Exception {
        Profesori existingProfesor = profesoriRepository.findById(id).orElse(null);

        if (existingProfesor == null) {
            throw new Exception("Not Found");
        }
        else {
            if (profesorDTO.getNume() != null)
                existingProfesor.setNume(profesorDTO.getNume());
            if (profesorDTO.getPrenume() != null)
                existingProfesor.setPrenume(profesorDTO.getPrenume());
            if (profesorDTO.getEmail() != null)
                existingProfesor.setEmail(profesorDTO.getEmail());
            if (profesorDTO.getGradDidactic() != null)
                existingProfesor.setGradDidactic(GradDidactic.valueOf(profesorDTO.getGradDidactic()));
            if (profesorDTO.getTipAsociere() != null)
                existingProfesor.setTipAsociere(TipAsociere.valueOf(profesorDTO.getTipAsociere()));
            if (profesorDTO.getAfiliere() != null)
                existingProfesor.setAfiliere(profesorDTO.getAfiliere());
            existingProfesor.setId(id);

            return this.createProfesor(GenericMapper.toProfesoriDTO(existingProfesor));
        }
    }
}
