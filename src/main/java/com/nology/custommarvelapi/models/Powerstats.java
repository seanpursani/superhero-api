package com.nology.custommarvelapi.models;

import javax.persistence.*;

@Entity
@Table(name = "POWERSTATS")
public class Powerstats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUPERHERO_ID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "SUPERHERO_ID")
    private Superhero superhero;

    @Column(name = "INTELLIGENCE")
    private Integer intelligence;

    @Column(name = "STRENGTH")
    private Integer strength;

    @Column(name = "SPEED")
    private Integer speed;

    @Column(name = "DURABILITY")
    private Integer durability;

    @Column(name = "POWER")
    private Integer power;

    @Column(name = "COMBAT")
    private Integer combat;

    public Powerstats() {
    }

    public Powerstats(Integer intelligence, Integer strength, Integer speed, Integer durability, Integer power, Integer combat) {
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
