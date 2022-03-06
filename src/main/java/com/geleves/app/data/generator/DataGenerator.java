package com.geleves.app.data.generator;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Parent;
import com.geleves.app.data.entity.Cours;
import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Enseignant;
import com.geleves.app.data.repository.ClasseRepository;
import com.geleves.app.data.repository.CoursRepository;
import com.geleves.app.data.repository.EleveRepository;
import com.geleves.app.data.repository.EnseignantRepository;
import com.geleves.app.data.repository.ParentRepository;
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
    							CoursRepository coursRepository, EnseignantRepository enseignantRepository) {

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
            
            ExampleDataGenerator<Enseignant> enseignantGenerator = new ExampleDataGenerator<>(Enseignant.class,
                    LocalDateTime.now());
            enseignantGenerator.setData(Enseignant::setNom, DataType.FIRST_NAME);
            enseignantGenerator.setData(Enseignant::setPrenom, DataType.LAST_NAME);
            enseignantGenerator.setData(Enseignant::setContact, DataType.PHONE_NUMBER);
            enseignantGenerator.setData(Enseignant::setEmail, DataType.EMAIL);
            enseignantGenerator.setData(Enseignant::setNombreDeClasses, DataType.NUMBER_UP_TO_10);
            List<Enseignant> enseignants = enseignantRepository.saveAll(enseignantGenerator.create(25, seed));
            
            
            ExampleDataGenerator<Classe> classeGenerator = new ExampleDataGenerator<>(Classe.class,
                    LocalDateTime.now());
            classeGenerator.setData(Classe::setNom, DataType.WORD);
            classeGenerator.setData(Classe::setNiveau, DataType.STATE);
            
            List<Classe> classes = classeGenerator.create(20, seed).stream().peek(class_ -> {
            	class_.setEnseignants(enseignants.subList(0, r.nextInt(enseignants.size())));
            }).collect(Collectors.toList());
           
            classeRepository.saveAll(classes);
            //classeRepository.saveAll(classeGenerator.create(20, seed));
            
            
            
            ExampleDataGenerator<Cours> coursGenerator = new ExampleDataGenerator<>(Cours.class,
                    LocalDateTime.now());
            coursGenerator.setData(Cours::setNom, DataType.WORD);
            coursGenerator.setData(Cours::setNiveau, DataType.STATE);
            coursGenerator.setData(Cours::setNombreDHeuresParSemaine, DataType.NUMBER_UP_TO_10);
            coursGenerator.setData(Cours::setCoefficient, DataType.NUMBER_UP_TO_10);
            coursGenerator.setData(Cours::setCode, DataType.DOMAIN);
            
            
            List<Cours> cours = coursGenerator.create(30, seed).stream().peek(cour -> {
                cour.setEnseignant(enseignants.get(r.nextInt(enseignants.size())));
                cour.setClasse(classes.get(r.nextInt(classes.size())));
             }).collect(Collectors.toList());
            
            coursRepository.saveAll(cours);
            
            
            ExampleDataGenerator<Parent> parentGenerator = new ExampleDataGenerator<>(Parent.class,
                    LocalDateTime.now());
            parentGenerator.setData(Parent::setNom, DataType.LAST_NAME);
            parentGenerator.setData(Parent::setPrenom, DataType.FIRST_NAME);
            parentGenerator.setData(Parent::setContact, DataType.PHONE_NUMBER);
            parentGenerator.setData(Parent::setAddress, DataType.ADDRESS);
            parentGenerator.setData(Parent::setEmail, DataType.EMAIL);
            List<Parent> parents = parentRepository.saveAll(parentGenerator.create(45, seed));
             
            String[] statuses = {"Normal", "Redoublant", "Recalcitrant", "Transfere", "Suspendu"};
            
            logger.info("... generating 50 Eleves entities...");
            ExampleDataGenerator<Eleve> eleveGenerator = new ExampleDataGenerator<>(Eleve.class,
                    LocalDateTime.now());
            eleveGenerator.setData(Eleve::setPrenom, DataType.FIRST_NAME);
            eleveGenerator.setData(Eleve::setNom, DataType.LAST_NAME);
            eleveGenerator.setData(Eleve::setAddresse, DataType.ADDRESS);
            eleveGenerator.setData(Eleve::setDateDeNaissance, DataType.DATE_OF_BIRTH);
            eleveGenerator.setData(Eleve::setNiveau, DataType.BOOK_TITLE_PREFIX);
            
            List<Eleve> eleves = eleveGenerator.create(50, seed).stream().peek(eleve -> {
               eleve.setStatut(statuses[r.nextInt(statuses.length)]);
               eleve.setParent(parents.get(r.nextInt(parents.size())));
            }).collect(Collectors.toList());

            eleveRepository.saveAll(eleves);

            logger.info("Generated demo data");
        };
    }

}
