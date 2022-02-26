package com.geleves.app.data.service;

import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Parent;
import com.geleves.app.data.repository.EleveRepository;
import com.geleves.app.data.repository.ParentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GelevesService {

    private final EleveRepository eleveRepository;
    private final ParentRepository parentRepository;

    public GelevesService(EleveRepository eleveRepository, ParentRepository parentRep) {
        this.eleveRepository = eleveRepository;
        this.parentRepository = parentRep;
    }

    public List<Eleve> findAllEleves(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return eleveRepository.findAll();
        } else {
            return eleveRepository.search(stringFilter);
        }
    }

    public long countEleves() {
        return eleveRepository.count();
    }

    public void deleteEleve(Eleve eleve) {
        eleveRepository.delete(eleve);
    }

    public void saveEleve(Eleve eleve) {
        if (eleve == null) {
            System.err.println("Eleve is null. Are you sure you have connected your form to the application?");
            return;
        }
        eleveRepository.save(eleve);
    }
    
    public List<Parent> findAllParents(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return parentRepository.findAll();
        } else {
            return parentRepository.search(stringFilter);
        }
    }
    
    public void deleteParent(Parent parent) {
        parentRepository.delete(parent);
    }

    public void saveParent(Parent parent) {
        if (parent == null) {
            System.err.println("Parent is null. Are you sure you have connected your form to the application?");
            return;
        }
        parentRepository.save(parent);
    }

   
}
