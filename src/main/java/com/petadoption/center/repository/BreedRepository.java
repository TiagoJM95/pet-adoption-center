package com.petadoption.center.repository;

import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, String> {
    Optional<Breed> findByName(String name);
    List<Breed> findBySpecies(Species species);
}