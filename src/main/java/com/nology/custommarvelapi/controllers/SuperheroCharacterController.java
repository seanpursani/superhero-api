package com.nology.custommarvelapi.controllers;

import com.nology.custommarvelapi.exceptions.SuperheroNotFoundException;
import com.nology.custommarvelapi.models.Superhero;
import com.nology.custommarvelapi.repositories.SuperheroCharacterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("superheroes")
public class SuperheroCharacterController {

    private final SuperheroCharacterRepository repository;

    SuperheroCharacterController(SuperheroCharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Superhero>> all() {
        List<Superhero> response = repository.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public Superhero one(@PathVariable Long id) throws SuperheroNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException(id, "Unable to find superhero"));
    }

    @PutMapping("/{id}")
    Superhero replaceEmployee(@RequestBody Superhero newSuperhero, @PathVariable Long id) {
        return repository.findById(id)
                .map(superhero -> {
                    superhero.setName(newSuperhero.getName());
                    superhero.setImageUrl(newSuperhero.getImageUrl());
                    superhero.setPowerstats(newSuperhero.getPowerstats());
                    return repository.save(superhero);
                })
                .orElseGet(() -> {
                    newSuperhero.setId(id);
                    return repository.save(newSuperhero);
                });
    }

    @DeleteMapping("/{id}")
    void deleteSuperhero(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
