package com.nology.superheroservices.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.nology.superheroservices.controllers.SuperheroCharacterController;
import com.nology.superheroservices.exceptions.SuperheroNotFoundException;
import com.nology.superheroservices.models.Superhero;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SuperheroModelAssembler implements RepresentationModelAssembler<Superhero, EntityModel<Superhero>> {

    @Override
    public EntityModel<Superhero> toModel(Superhero superhero) {
        try {
            return EntityModel.of(superhero,
                    linkTo(methodOn(SuperheroCharacterController.class).oneSuperhero(superhero.getId())).withSelfRel(),
                    linkTo(methodOn(SuperheroCharacterController.class).allSuperheroes()).withRel("superheroes"));
        } catch (SuperheroNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}