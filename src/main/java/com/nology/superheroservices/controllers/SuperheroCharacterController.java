package com.nology.superheroservices.controllers;

import com.nology.superheroservices.exceptions.FightNotFoundException;
import com.nology.superheroservices.exceptions.SuperheroNotFoundException;
import com.nology.superheroservices.models.Fight;
import com.nology.superheroservices.models.Superhero;
import com.nology.superheroservices.repositories.FightRepository;
import com.nology.superheroservices.repositories.SuperheroRepository;
import com.nology.superheroservices.services.FightModelAssembler;
import com.nology.superheroservices.services.SuperheroModelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class SuperheroCharacterController {

    private final SuperheroModelAssembler superheroModelAssembler;
    private final SuperheroRepository superheroRepository;
    private final FightModelAssembler fightModelAssembler;
    private final FightRepository fightRepository;

    public SuperheroCharacterController(SuperheroModelAssembler superheroModelAssembler, SuperheroRepository superheroRepository, FightModelAssembler fightModelAssembler, FightRepository fightRepository) {
        this.superheroModelAssembler = superheroModelAssembler;
        this.superheroRepository = superheroRepository;
        this.fightModelAssembler = fightModelAssembler;
        this.fightRepository = fightRepository;
    }


    @GetMapping("/superheroes")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Superhero>> allSuperheroes() {
        List<EntityModel<Superhero>> superheroes = superheroRepository.findAll().stream()
                .map(superheroModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(superheroes, linkTo(methodOn(SuperheroCharacterController.class).allSuperheroes()).withSelfRel());
    }

    @GetMapping("/superheroes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Superhero> oneSuperhero(@PathVariable Long id) throws SuperheroNotFoundException {
        Superhero response = superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException(id, "Unable to find superhero "));
        return superheroModelAssembler.toModel(response);
    }

    @GetMapping("superheroes/{id}/fights")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Fight>> getAllFightsBySuperheroID(@PathVariable Long id) throws SuperheroNotFoundException {
        Set<Fight> fightsList = superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException(id, "Superhero not found "))
                .getFights();
        List<EntityModel<Fight>> fightEntityModel = fightsList.stream()
                .map(fightModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(fightEntityModel, linkTo(methodOn(FightController.class).allFights()).withSelfRel());
    }

    @PostMapping("/superheroes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> newSuperhero(@RequestBody Superhero newSuperhero) {
        EntityModel<Superhero> entityModel = superheroModelAssembler.toModel((superheroRepository.save(newSuperhero)));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/fights/{fightId}/superhero")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Superhero> newFight(@RequestBody Superhero superheroRequest, @PathVariable Long fightId) throws SuperheroNotFoundException, FightNotFoundException {
        Superhero superhero = fightRepository.findById(fightId).map(fight -> {
            Long superheroId = superheroRequest.getId();
            if (superheroId != 0L) {
                try {
                    Superhero _superhero = superheroRepository.findById(superheroId)
                            .orElseThrow(() -> new SuperheroNotFoundException(superheroId, "Superhero not found "));
                    fight.addSuperhero(_superhero);
                    fightRepository.save(fight);
                } catch (SuperheroNotFoundException e) {
                    e.printStackTrace();
                }
            }
            fight.addSuperhero(superheroRequest);
            return superheroRepository.save(superheroRequest);
        }).orElseThrow(() -> new FightNotFoundException(fightId, "Fight not found "));
        return superheroModelAssembler.toModel(superhero);
    }

    @PutMapping("/superheroes/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> replaceSuperhero(@RequestBody Superhero newSuperhero, @PathVariable Long id) throws SuperheroNotFoundException {
        Superhero updatedSuperhero = superheroRepository.findById(id)
                .map(superhero -> {
                    superhero.setName(newSuperhero.getName());
                    superhero.setImageUrl(newSuperhero.getImageUrl());
                    superhero.setPowerstats(newSuperhero.getPowerstats());
                    return superheroRepository.save(superhero);
                })
                .orElseThrow(() -> new SuperheroNotFoundException(id, "Unable to find superhero "));
        EntityModel<Superhero> entityModel = superheroModelAssembler.toModel(updatedSuperhero);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/superheroes")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteAllSuperheroes() {
        superheroRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/superheroes/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteSuperhero(@PathVariable Long id) {
        superheroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
