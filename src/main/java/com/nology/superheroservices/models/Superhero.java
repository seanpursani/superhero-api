package com.nology.superheroservices.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "superhero")
public class Superhero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne(cascade = CascadeType.ALL)
    private Powerstats powerstats;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.REFRESH
        },
        mappedBy = "superheroes")
    @JsonIgnore
    private Set<Fight> fights = new HashSet<>();

    @JsonIdentityInfo(scope = Superhero.class, generator= ObjectIdGenerators.PropertyGenerator.class, property="id") // ignore all other properties except id
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Superhero() {
    }

    public Superhero(Long id, String name, String imageUrl, Powerstats powerstats, Set<Fight> fights, User user) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.powerstats = powerstats;
        this.fights = fights;
        this.user = user;
    }

    public Superhero(String name, String imageUrl, Powerstats powerstats) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.powerstats = powerstats;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Powerstats getPowerstats() {
        return powerstats;
    }

    public void setPowerstats(Powerstats powerstats) {
        this.powerstats = powerstats;
    }

    public Set<Fight> getFights() {
        return fights;
    }

    public void setFights(Set<Fight> fights) {
        this.fights = fights;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superhero)) return false;
        return id != null && id.equals(((Superhero) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
