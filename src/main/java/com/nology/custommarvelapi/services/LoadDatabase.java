package com.nology.custommarvelapi.services;

import com.nology.custommarvelapi.models.Powerstats;
import com.nology.custommarvelapi.models.Superhero;
import com.nology.custommarvelapi.repositories.SuperheroCharacterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(SuperheroCharacterRepository repository) throws Exception {
        return args -> {
            Powerstats stats = new Powerstats(0, 0, 0, 0, 0, 0);
            log.info("Preloading " + repository.save(new Superhero("Sean", "test.url", stats, 0, 0)));
        };
    }
}

//    List<Superhero> superheroes = new ArrayList<>();
//    @Value("${superHeroApiPublicKey}")
//    public String apiKey;
//            try {
//                int totalCharacters = 731;
//                for (int i = 1; i < totalCharacters; i++) {
//                    JsonObject characterJson = JsonWebService.getJsonObjectFromUrl("https://superheroapi.com/api/" + apiKey + "/" + i);
//                    System.out.println(characterJson);
//                    JsonObject powerStatsJson = characterJson.get("powerstats").getAsJsonObject();
//                    PowerStats powerStats = new PowerStats(
//                            JsonMapperService.getIntFromJson(powerStatsJson, "intelligence"),
//                            JsonMapperService.getIntFromJson(powerStatsJson, "strength"),
//                            JsonMapperService.getIntFromJson(powerStatsJson, "speed"),
//                            JsonMapperService.getIntFromJson(powerStatsJson, "durability"),
//                            JsonMapperService.getIntFromJson(powerStatsJson, "power"),
//                            JsonMapperService.getIntFromJson(powerStatsJson, "combat")
//                    );
//                    log.info("Preloading " + repository.save(new Superhero(
//                            JsonMapperService.getStringFromJson(characterJson, "name"),
//                            JsonMapperService.getStringFromJson(characterJson, "image"),
//                            powerStats, 0, 0)
//                    ));
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("Error initialising database with characters from Superhero API.");
//            }
//        };


