package com.geleves.app.data.service;

import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Cours;
import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Enseignant;
import com.geleves.app.data.entity.Parent;
import com.geleves.app.data.repository.ClasseRepository;
import com.geleves.app.data.repository.CoursRepository;
import com.geleves.app.data.repository.EleveRepository;
import com.geleves.app.data.repository.EnseignantRepository;
import com.geleves.app.data.repository.ParentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GelevesService {

    private final EleveRepository eleveRepository;
    private final ParentRepository parentRepository;
    private final EnseignantRepository enseignantRepository;
    private final CoursRepository coursRepository;
    private final ClasseRepository classeRepository;

    public GelevesService(EleveRepository eleveRepository, ParentRepository parentRep, 
    		EnseignantRepository enseignantRep, CoursRepository coursRepository, ClasseRepository classeRepository) {
        this.eleveRepository = eleveRepository;
        this.parentRepository = parentRep;
        this.enseignantRepository = enseignantRep;
        this.coursRepository = coursRepository;
        this.classeRepository = classeRepository;
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
            System.err.println("Object 'eleve' is null. Are you sure you have connected your form to the application?");
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
            System.err.println("Object is null. Are you sure you have connected your form to the application?");
            return;
        }
        parentRepository.save(parent);
    }
    
    public List<Enseignant> findAllEnseignants(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return enseignantRepository.findAll();
        } else {
            return enseignantRepository.search(stringFilter);
        }
    }
    
    
    public List<Cours> findCoursByEnseignantId(Enseignant enseignant){
    	
    	return coursRepository.getCoursByEnseignant(enseignant.getId());
    }
    public long countEnseignants() {
        return enseignantRepository.count();
    }

    public void deleteEnseignant(Enseignant enseignant) {
        enseignantRepository.delete(enseignant);
    }

    public void saveEnseignant(Enseignant enseignant) {
        if (enseignant == null) {
            System.err.println("Object is null. Are you sure you have connected your form to the application?");
            return;
        }
        enseignantRepository.save(enseignant);
    }
    
    public List<Cours> findAllCours() {
            return coursRepository.findAll();
    }
    public long countCours() {
        return coursRepository.count();
    }

    public void deleteCours(Cours cours) {
        coursRepository.delete(cours);
    }

    public void saveCours(Cours cours) {
        if (cours == null) {
            System.err.println("Object is null. Are you sure you have connected your form to the application?");
            return;
        }
        coursRepository.save(cours);
    }
    
    public List<Classe> findAllClasses() {
	    return classeRepository.findAll();
	}
	public long countClasses() {
	    return classeRepository.count();
	}
	
	public void deleteCours(Classe classe) {
	    classeRepository.delete(classe);
	}
	
	public void saveCours(Classe classe) {
	    if (classe == null) {
	        System.err.println("Object is null. Are you sure you have connected your form to the application?");
	        return;
	    }
	    classeRepository.save(classe);
	}
   
}
