package com.geleves.app.data.generator;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Parent;
import com.geleves.app.data.entity.Cours;
import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Enseignant;
import com.geleves.app.data.entity.Niveau;
import com.geleves.app.data.entity.Note;
import com.geleves.app.data.entity.Periode;
import com.geleves.app.data.repository.ClasseRepository;
import com.geleves.app.data.repository.CoursRepository;
import com.geleves.app.data.repository.EleveRepository;
import com.geleves.app.data.repository.EnseignantRepository;
import com.geleves.app.data.repository.NiveauRepository;
import com.geleves.app.data.repository.NoteRepository;
import com.geleves.app.data.repository.ParentRepository;
import com.geleves.app.data.repository.PeriodeRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(EleveRepository eleveRepository, ParentRepository parentRepository, ClasseRepository classeRepository,
    							CoursRepository coursRepository, EnseignantRepository enseignantRepository, NiveauRepository niveauRepository,
    							NoteRepository noteRepository, PeriodeRepository periodeRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (eleveRepository.count() != 0L && parentRepository.count() != 0L && enseignantRepository.count() != 0L
            		&& coursRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;
            Random r = new Random(seed);
            
            logger.info("Generating demo data");
            
            ExampleDataGenerator<Periode> periodeGenerator = new ExampleDataGenerator<>(Periode.class,
                    LocalDateTime.now());
            periodeGenerator.setData(Periode::setDateDeRentree, DataType.DATE_NEXT_1_YEAR);
            periodeGenerator.setData(Periode::setDateGrandeVacance, DataType.DATE_NEXT_30_DAYS);
            
            List<Periode> periodes = periodeRepository.saveAll(periodeGenerator.create(45, seed).stream().peek(periode->{
            	byte trimestre = (byte)(1+r.nextInt(3));
            	periode.setAnneeScolaire("2021-2022");
            	periode.setTrimestre(trimestre);
            }).collect(Collectors.toList()));
            
            ExampleDataGenerator<Parent> parentGenerator = new ExampleDataGenerator<>(Parent.class,
                    LocalDateTime.now());
            parentGenerator.setData(Parent::setNom, DataType.LAST_NAME);
            parentGenerator.setData(Parent::setPrenom, DataType.FIRST_NAME);
            parentGenerator.setData(Parent::setContact, DataType.PHONE_NUMBER);
            parentGenerator.setData(Parent::setAddress, DataType.ADDRESS);
            parentGenerator.setData(Parent::setEmail, DataType.EMAIL);
            List<Parent> parents = parentRepository.saveAll(parentGenerator.create(45, seed));
            
            ExampleDataGenerator<Enseignant> enseignantGenerator = new ExampleDataGenerator<>(Enseignant.class,
                    LocalDateTime.now());
            enseignantGenerator.setData(Enseignant::setNom, DataType.FIRST_NAME);
            enseignantGenerator.setData(Enseignant::setPrenom, DataType.LAST_NAME);
            enseignantGenerator.setData(Enseignant::setContact, DataType.PHONE_NUMBER);
            enseignantGenerator.setData(Enseignant::setEmail, DataType.EMAIL);
            HashSet<Enseignant> enseignants = (HashSet<Enseignant>) enseignantGenerator.create(25, seed).stream().collect(Collectors.toSet());
            
            
            
            String[] niveaux_ = {"6eme", "5eme", "4eme", "3eme", "2nd", "1ere", "Tle"};
            HashMap<String, Niveau> niveaux = new HashMap<String, Niveau>(7);
            Set<Niveau> nivoToSave = new HashSet<Niveau>();
            for(String nivo: niveaux_) {
            	Niveau niv = new Niveau();
            	niv.setNiveau(nivo);
            	niv.setEnseignants(enseignants.stream().limit(r.nextInt(enseignants.size())).collect(Collectors.toSet()));
            	nivoToSave.add(niv);
            	niveaux.put(nivo, niv);
            }
            niveauRepository.saveAll(nivoToSave);
            
            for(Enseignant enseign: enseignants) {
            	enseign.setNiveaux(nivoToSave.stream().limit(r.nextInt(nivoToSave.size())).collect(Collectors.toSet()));
            }
            
            String[] classes_ = {"6eme A", "5eme A", "4eme A", "3eme A", "2nd L1", "1ere L1", "Tle L1",
            		"6eme B", "5eme B", "4eme B", "3eme B", "2nd S1", "1ere S1", "Tle D1",
            		"6eme C", "5eme C", "4eme C", "3eme C", "2nd S2", "1ere S2", "Tle D2", "2nd L2",
            		"1ere L2", "Tle C", "Tle L2"};
            HashSet<Classe> classes = new HashSet<Classe>(classes_.length);
            for(String classe : classes_) {
            	Classe class_ = new Classe();
            	class_.setNom(classe);
            	class_.setNiveau(niveaux.get((classe.split(" "))[0]));
            	class_.setEnseignants(enseignants.stream().limit(r.nextInt(enseignants.size())).collect(Collectors.toSet()));
            	//class_.setChefDeClasse(chefDeClasse);
            	classes.add(class_);
            }
            
            classeRepository.saveAll(classes);
            
            for(Enseignant enseign: enseignants) {
            	enseign.setClasses(classes.stream().limit(r.nextInt(classes.size())).collect(Collectors.toSet()));
            }
            
            enseignantRepository.saveAll(enseignants);
            
            
            String[] statuses = {"Normal", "Redoublant", "Recalcitrant", "Transfere", "Suspendu"};
            
            logger.info("... generating 50 Eleves entities...");
            ExampleDataGenerator<Eleve> eleveGenerator = new ExampleDataGenerator<>(Eleve.class,
                    LocalDateTime.now());
            eleveGenerator.setData(Eleve::setPrenom, DataType.FIRST_NAME);
            eleveGenerator.setData(Eleve::setNom, DataType.LAST_NAME);
            eleveGenerator.setData(Eleve::setAddresse, DataType.ADDRESS);
            eleveGenerator.setData(Eleve::setDateDeNaissance, DataType.DATE_OF_BIRTH);
            eleveGenerator.setData(Eleve::setDateDInscription, DataType.DATE_LAST_10_YEARS);
            eleveGenerator.setData(Eleve::setMatricule, DataType.IBAN);
            
            List<Eleve> eleves = eleveGenerator.create(50, seed).stream().peek(eleve -> {
               eleve.setStatut(statuses[r.nextInt(statuses.length)]);
               eleve.setParent(parents.get(r.nextInt(parents.size())));
               Classe randClasse = (Classe) getRandomEltFromSet(classes);
               eleve.setClasse(randClasse);
               eleve.setNiveau(randClasse.getNiveau());
            }).collect(Collectors.toList());

            eleveRepository.saveAll(eleves);
            
            ExampleDataGenerator<Cours> coursGenerator = new ExampleDataGenerator<>(Cours.class,
                    LocalDateTime.now());
            coursGenerator.setData(Cours::setNom, DataType.WORD);
            coursGenerator.setData(Cours::setNombreDHeuresParSemaine, DataType.NUMBER_UP_TO_10);
            coursGenerator.setData(Cours::setCode, DataType.DOMAIN);
            
            
            List<Cours> cours = coursGenerator.create(30, seed).stream().peek(cour -> {
            	byte coef = (byte) (1 + r.nextInt(5));
            	cour.setCoefficient(coef);
                cour.setEnseignant((Enseignant) getRandomEltFromSet(enseignants));
                Classe randClasse = (Classe) getRandomEltFromSet(classes);
                cour.setClasse(randClasse);
                cour.setNiveau(randClasse.getNiveau());
             }).collect(Collectors.toList());
            
            coursRepository.saveAll(cours);
            
            ExampleDataGenerator<Note> noteGenerator = new ExampleDataGenerator<>(Note.class,
                    LocalDateTime.now());

            noteGenerator.setData(Note::setType_dexamen, DataType.CITY);
            noteGenerator.setData(Note::setCommentaire, DataType.BOOK_TITLE);
            List<Note> notes = noteRepository.saveAll(noteGenerator.create(60, seed).stream().peek(note -> {
            	note.setEleve(eleves.get(r.nextInt(eleves.size())));
            	note.setMatiere(cours.get(r.nextInt(cours.size())));
            	note.setNote(r.nextFloat()*20);
            	note.setPeriode(periodes.get(r.nextInt(periodes.size())));
            }).collect(Collectors.toList()));
            

            logger.info("Generated demo data");
        };
    }
    
    public <T> Object getRandomEltFromSet(Set<T> set) {
    	int item = new Random().nextInt(set.size()); 
    	int i = 0;
    	for(Object obj : set)
    	{
    	    if (i == item)
    	        return obj;
    	    i++;
    	}
		return null;
    }

}
