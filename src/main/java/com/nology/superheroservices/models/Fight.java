package com.nology.superheroservices.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "fight")
public class Fight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "winner")
    private String winner;

    @JsonIdentityInfo(scope= Fight.class, generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "fight_superhero",
            joinColumns = @JoinColumn(name = "fight_id"),
            inverseJoinColumns = @JoinColumn(name = "superhero_id")
    )
    Set<Superhero> superheroes = new HashSet<>();

    public Fight() {
    }

    public Fight(Long id, String winner, User user, Set<Superhero> superheroes) {
        this.id = id;
        this.winner = winner;
        this.user = user;
        this.superheroes = superheroes;
    }

    public void addSuperhero(Superhero superhero) {
        superheroes.add(superhero);
        superhero.getFights().add(this);
    }

    public void removeSuperhero(Long superheroId) {
        Superhero superhero = this.superheroes.stream().filter(s -> s.getId() == superheroId).findFirst().orElse(null);
        superheroes.remove(superhero);
        assert superhero != null;
        superhero.getFights().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Superhero> getSuperheroes() {
        return superheroes;
    }

    public void setSuperheroes(Set<Superhero> superheroes) {
        this.superheroes = superheroes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fight fight = (Fight) o;
        return Objects.equals(id, fight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
