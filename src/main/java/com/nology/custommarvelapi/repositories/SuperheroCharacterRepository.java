package com.nology.custommarvelapi.repositories;

import com.nology.custommarvelapi.models.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperheroCharacterRepository extends JpaRepository<Superhero, Long> {
}
