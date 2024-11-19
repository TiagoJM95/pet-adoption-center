package com.petadoption.center.repository;

import com.petadoption.center.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, String> {
    Optional<Species> findByName(String name);
}