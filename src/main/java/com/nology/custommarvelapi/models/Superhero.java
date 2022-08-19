package com.nology.custommarvelapi.models;

import javax.persistence.*;

@Entity
@Table(name = "SUPERHERO")
public class Superhero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @OneToOne(mappedBy = "superhero", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Powerstats powerstats;

    @Column(name = "TOTAL_FIGHTS")
    private Integer totalFights;

    @Column(name = "TOTAL_FIGHTS_WON")
    private Integer totalFightsWon;

    public Superhero() {
    }

    public Superhero(String name, String imageUrl, Powerstats powerstats, Integer totalFights, Integer totalFightsWon) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.powerstats = powerstats;
        this.totalFights = totalFights;
        this.totalFightsWon = totalFightsWon;
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

    public Integer getTotalFights() {
        return totalFights;
    }

    public void setTotalFights(Integer totalFights) {
        this.totalFights = totalFights;
    }

    public Integer getTotalFightsWon() {
        return totalFightsWon;
    }

    public void setTotalFightsWon(Integer totalFightsWon) {
        this.totalFightsWon = totalFightsWon;
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
