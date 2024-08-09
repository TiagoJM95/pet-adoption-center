package com.petadoption.center.repository;

import com.petadoption.center.model.PetBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {
}
