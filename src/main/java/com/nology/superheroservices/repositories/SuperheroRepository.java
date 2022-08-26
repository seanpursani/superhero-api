package com.nology.superheroservices.repositories;

import com.nology.superheroservices.models.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperheroRepository extends JpaRepository<Superhero, Long> {
}
