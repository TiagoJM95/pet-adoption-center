package com.petadoption.center.repository;

import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findByName(String name);

    List<Optional<Breed>> findBySpecies(Species species);
}
