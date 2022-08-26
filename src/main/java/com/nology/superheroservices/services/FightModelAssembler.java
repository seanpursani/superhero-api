package com.nology.superheroservices.services;

import com.nology.superheroservices.controllers.FightController;
import com.nology.superheroservices.exceptions.FightNotFoundException;
import com.nology.superheroservices.models.Fight;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FightModelAssembler implements RepresentationModelAssembler<Fight, EntityModel<Fight>> {

    @Override
    public EntityModel<Fight> toModel(Fight fight) {
        try {
            return EntityModel.of(fight,
                    linkTo(methodOn(FightController.class).oneFight(fight.getId())).withSelfRel(),
                    linkTo(methodOn(FightController.class).allFights()).withRel("fights"));
        } catch (FightNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
