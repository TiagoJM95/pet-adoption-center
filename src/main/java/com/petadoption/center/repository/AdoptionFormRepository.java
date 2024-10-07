package com.petadoption.center.repository;

import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionFormRepository extends JpaRepository<AdoptionForm, String> {

    List<AdoptionForm> findByUserId(User userId);

    List<AdoptionForm> findByPetId(Pet petId);
}