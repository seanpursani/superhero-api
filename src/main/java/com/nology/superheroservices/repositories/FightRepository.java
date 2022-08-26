package com.nology.superheroservices.repositories;

import com.nology.superheroservices.models.Fight;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FightRepository extends JpaRepository<Fight, Long> {
}
