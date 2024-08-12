package com.petadoption.center.repository;

import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionFormRepository extends JpaRepository<AdoptionForm, Long> {

    List<Optional<AdoptionForm>> findByUserId(User userId);

    List<Optional<AdoptionForm>> findByPetId(Long petId);
}
