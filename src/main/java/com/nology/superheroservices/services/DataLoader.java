package com.nology.superheroservices.services;

import com.google.gson.JsonObject;
import com.nology.superheroservices.models.Powerstats;
import com.nology.superheroservices.models.Superhero;
import com.nology.superheroservices.repositories.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;

@Component
public class DataLoader implements ApplicationRunner {

    private final SuperheroRepository superheroRepository;
    @Value("${superheroApiPublicKey}")
    private String apiKey;


    @Autowired
    public DataLoader(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            this.seedSuperheroes();
        } catch (Exception e) {
            throw new ServiceUnavailableException("Unable to seed superheroes. Exiting program...");
        }
    }

    private void seedSuperheroes() throws Exception {
        try {
            Powerstats powerstats = new Powerstats(75, 34, 87, 34, 67, 56);
            Superhero superhero = new Superhero("Evil Deadpool", "https://www.superherodb.com/pictures2/portraits/10/100/10090.jpg", powerstats);
            superheroRepository.save(superhero);
        } catch (Exception e) {
            throw new Exception("Error seeding characters from Marvel API.");
        }
    }
}


