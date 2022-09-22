package com.nology.superheroservices.controllers;

import com.nology.superheroservices.exceptions.FightNotFoundException;
import com.nology.superheroservices.exceptions.SuperheroNotFoundException;
import com.nology.superheroservices.exceptions.UserNotFoundException;
import com.nology.superheroservices.models.Fight;
import com.nology.superheroservices.models.Superhero;
import com.nology.superheroservices.repositories.FightRepository;
import com.nology.superheroservices.repositories.SuperheroRepository;
import com.nology.superheroservices.repositories.UserRepository;
import com.nology.superheroservices.services.FightModelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class FightController {

    private final FightRepository fightRepository;
    private final UserRepository userRepository;
    private final SuperheroRepository superheroRepository;
    private final FightModelAssembler fightModelAssembler;

    public FightController(FightRepository fightRepository, UserRepository userRepository, SuperheroRepository superheroRepository, FightModelAssembler fightModelAssembler) {
        this.fightRepository = fightRepository;
        this.userRepository = userRepository;
        this.superheroRepository = superheroRepository;
        this.fightModelAssembler = fightModelAssembler;
    }

    @GetMapping("/fights")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Fight>> allFights() {
        List<EntityModel<Fight>> fights = fightRepository.findAll().stream()
                .map(fightModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(fights, linkTo(methodOn(FightController.class).allFights()).withSelfRel());
    }

    @GetMapping("/fights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Fight> oneFight(@PathVariable Long id) throws FightNotFoundException {
        Fight response = fightRepository.findById(id)
                .orElseThrow(() -> new FightNotFoundException (id, "Unable to find fight "));
        return fightModelAssembler.toModel(response);
    }

    @PostMapping("/fights/{supIds}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<Fight>> newFight(@RequestBody Fight newFight, @PathVariable List<Long> supIds) throws UserNotFoundException {
        userRepository.findById(newFight.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException((long) -1, "Unable to find fight"));
        superheroRepository.findAllById(supIds).forEach(newFight::addSuperhero);
        EntityModel<Fight> entityModel = fightModelAssembler.toModel((fightRepository.save(newFight)));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/fights/{fightId}/{supId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateFightWinner(@PathVariable Long fightId, @PathVariable Long supId) throws SuperheroNotFoundException, FightNotFoundException {
        Superhero superhero = superheroRepository.findById(supId)
                .orElseThrow(() -> new SuperheroNotFoundException(supId, "Unable to find superhero "));
        Fight updatedFight = fightRepository.findById(fightId)
                .map(fight -> {
                    if (fight.getSuperheroes().contains(superhero)) {
                        fight.setWinner(superhero.getName());
                    } else {
                        throw new IllegalArgumentException("Requested fighter did not fight");
                    }
                    return fightRepository.save(fight);
                })
                .orElseThrow(() -> new FightNotFoundException(fightId, "Unable to find requested fight "));
        EntityModel<Fight> entityModel = fightModelAssembler.toModel(updatedFight);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/fights")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteAllFights() {
        fightRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/fights/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteFight(@PathVariable Long id) {
        fightRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
