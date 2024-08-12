package com.petadoption.center.repository;

import com.petadoption.center.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByName(String name);

    Optional<Organization> findByEmail(String email);

    Optional<Organization> findByPhoneNumber(String phoneNumber);

    List<Optional<Organization>> findByCity(String city);

    List<Optional<Organization>> findByState(String state);

    List<Optional<Organization>> findByPostalCode(String postalCode);
}
