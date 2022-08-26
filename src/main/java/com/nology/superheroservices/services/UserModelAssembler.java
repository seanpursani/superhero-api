package com.nology.superheroservices.services;

import com.nology.superheroservices.controllers.UserController;
import com.nology.superheroservices.exceptions.UserNotFoundException;
import com.nology.superheroservices.models.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>>{
    @Override
    public EntityModel<User> toModel(User fight) {
        try {
            return EntityModel.of(fight,
                    linkTo(methodOn(UserController.class).oneUser(fight.getId())).withSelfRel(),
                    linkTo(methodOn(UserController.class).allUsers()).withRel("users"));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

