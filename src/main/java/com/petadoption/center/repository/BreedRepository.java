package com.petadoption.center.repository;

import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, String> {
    List<Breed> findBySpecies(Species species);
}