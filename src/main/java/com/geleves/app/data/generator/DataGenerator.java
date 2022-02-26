package com.geleves.app.data.generator;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Parent;
import com.geleves.app.data.repository.EleveRepository;
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
    public CommandLineRunner loadData(EleveRepository eleveRepository, ParentRepository parentRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (eleveRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            
            ExampleDataGenerator<Parent> parentGenerator = new ExampleDataGenerator<>(Parent.class,
                    LocalDateTime.now());
            parentGenerator.setData(Parent::setNom, DataType.LAST_NAME);
            parentGenerator.setData(Parent::setPrenom, DataType.FIRST_NAME);
            parentGenerator.setData(Parent::setContact, DataType.PHONE_NUMBER);
            parentGenerator.setData(Parent::setAddress, DataType.ADDRESS);
            parentGenerator.setData(Parent::setEmail, DataType.EMAIL);
            List<Parent> parents = parentRepository.saveAll(parentGenerator.create(45, seed));
             
            String[] statuses = {"Normal", "Redoublant", "Recalcitrant", "Transfere", "Suspendu"};
            Random r = new Random(seed);
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
