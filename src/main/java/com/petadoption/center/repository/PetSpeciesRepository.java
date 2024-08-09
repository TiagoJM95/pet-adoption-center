package com.petadoption.center.repository;

import com.petadoption.center.model.PetSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetSpeciesRepository extends JpaRepository<PetSpecies, Long> {
}