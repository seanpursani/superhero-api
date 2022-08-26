package com.nology.superheroservices.controllers;

import com.nology.superheroservices.exceptions.UserNotFoundException;
import com.nology.superheroservices.models.User;
import com.nology.superheroservices.repositories.UserRepository;
import com.nology.superheroservices.services.UserModelAssembler;

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
public class UserController {

    private final UserModelAssembler assembler;
    private final UserRepository repository;

    public UserController(UserModelAssembler assembler, UserRepository repository) {
        this.assembler = assembler;
        this.repository = repository;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<User>> allUsers() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).allUsers()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<User> oneUser(@PathVariable Long id) throws UserNotFoundException {
        User response = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id, "Unable to find user "));
        return assembler.toModel(response);
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> newUser (@RequestBody User newUser) {
        EntityModel<User> entityModel = assembler.toModel((repository.save(newUser)));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}

