package com.nology.superheroservices.services;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nology.superheroservices.models.Powerstats;
import com.nology.superheroservices.models.Superhero;
import com.nology.superheroservices.repositories.SuperheroRepository;
import com.nology.superheroservices.util.SuperheroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SuperheroDataLoader implements ApplicationRunner {

    private final SuperheroRepository superheroRepository;
    @Value("${marvelApiPublicKey}")
    public String publicKey;
    @Value("${marvelApiPrivateKey}")
    public String privateKey;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Autowired
    public SuperheroDataLoader(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<Superhero> superheroList = new ArrayList<>();
            for (int i = 97; i < 122; i++) { // Iterate over alphabet's equivalent ASCII number and cast to char
                this.seedSuperheroes((char) i, superheroList);
                superheroList.clear();
            }

        } catch (Exception e) {
            throw new ServiceUnavailableException("Unable to seed superheroes. Exiting program...");
        }
    }

    private void seedSuperheroes(char startsWith, List<Superhero> superheroList) throws Exception {
        try {
            System.out.println("Seeding 20 Characters");
            System.out.println(LocalDateTime.now());
            String location = "https://gateway.marvel.com/v1/public/characters?"
                    + "nameStartsWith=" + startsWith + "&ts="
                    + SuperheroUtil.timeStamp + "&apikey="
                    + publicKey + "&hash="
                    + SuperheroUtil.MD5hash(publicKey, privateKey, SuperheroUtil.timeStamp);

            JsonObject rootObj = JsonWebService.getJsonObjectFromUrl(location);
            JsonObject title = rootObj.get("data").getAsJsonObject();
            JsonArray results = title.get("results").getAsJsonArray();

            results.forEach(c -> {
                JsonObject characterJson = c.getAsJsonObject();

                JsonObject tJson = characterJson.get("thumbnail").getAsJsonObject();
                String image = JsonMapperService.getStringFromJson(tJson, "path")
                        + "." + JsonMapperService.getStringFromJson(tJson, "extension");

                Powerstats powerstats = new Powerstats(
                        SuperheroUtil.getRandomNumber(40, 100),
                        SuperheroUtil.getRandomNumber(40, 100),
                        SuperheroUtil.getRandomNumber(40, 100),
                        SuperheroUtil.getRandomNumber(40, 100),
                        SuperheroUtil.getRandomNumber(40, 100),
                        SuperheroUtil.getRandomNumber(40, 100)
                );

                Superhero superhero = new Superhero(
                        JsonMapperService.getStringFromJson(characterJson, "name"),
                        image,
                        powerstats
                );
                superheroList.add(superhero);
            });
        } catch (Exception e) {
            throw new Exception("Error seeding characters from Marvel API.");
        }
        superheroRepository.saveAll(superheroList);
        System.out.println(LocalDateTime.now());
    }
}


