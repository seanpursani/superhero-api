package com.nology.superheroservices.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "powerstats")
public class Powerstats implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy="powerstats", cascade = CascadeType.ALL)
    private Superhero superhero;

    @Column(name = "intelligence", nullable = false)
    private Integer intelligence;

    @Column(name = "strength", nullable = false)
    private Integer strength;

    @Column(name = "speed", nullable = false)
    private Integer speed;

    @Column(name = "durability", nullable = false)
    private Integer durability;

    @Column(name = "power", nullable = false)
    private Integer power;

    @Column(name = "combat", nullable = false)
    private Integer combat;

    public Powerstats() {
    }

    public Powerstats(Long id, Superhero superhero, Integer intelligence, Integer strength, Integer speed, Integer durability, Integer power, Integer combat) {
        this.id = id;
        this.superhero = superhero;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Superhero getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDurability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCombat() {
        return combat;
    }

    public void setCombat(Integer combat) {
        this.combat = combat;
    }

}
