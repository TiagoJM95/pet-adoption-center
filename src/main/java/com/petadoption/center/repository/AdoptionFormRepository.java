package com.petadoption.center.repository;

import com.petadoption.center.model.AdoptionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionFormRepository extends JpaRepository<AdoptionForm, Long> {
}
