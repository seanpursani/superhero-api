package com.nology.superheroservices.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"") //escape the table name when using reserved keywords
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true) // prevent infinite recursion/ circular referencing in bidirectional entities
    private List<Superhero> userCreatedHeroes;

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fight> fights = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, List<Superhero> userCreatedHeroes, List<Fight> fights) {
        this.id = id;
        this.name = name;
        this.userCreatedHeroes = userCreatedHeroes;
        this.fights = fights;
    }

    public void addSuperhero(Superhero superhero) {
        if (userCreatedHeroes.contains(superhero))
            return ;
        this.userCreatedHeroes.add(superhero);
        superhero.setUser(this);
    }

    public void removeSuperhero(Superhero superhero) {
        if (!userCreatedHeroes.contains(superhero))
            return ;
        this.userCreatedHeroes.remove(superhero);
        superhero.setUser(null);
    }

    public void addFight(Fight fight) {
        if (fights.contains(fight))
            return ;
        this.fights.add(fight);
        fight.setUser(this);
    }

    public void removeFight(Fight fight) {
        if (!fights.contains(fight))
            return ;
        this.fights.remove(fight);
        fight.setUser(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Superhero> getUserCreatedHeroes() {
        return userCreatedHeroes;
    }

    public void setUserCreatedHeroes(List<Superhero> userCreatedHeroes) {
        this.userCreatedHeroes = userCreatedHeroes;
    }

    public List<Fight> getFights() {
        return fights;
    }

    public void setFights(List<Fight> fights) {
        this.fights = fights;
    }

}
