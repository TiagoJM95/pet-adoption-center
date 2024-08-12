package com.petadoption.center.repository;

import com.petadoption.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(Integer phoneNumber);

    List<User> findByAddress_City(String city);

    List<User> findByAddress_State(String state);

    List<User> findByAddress_PostalCode(String postalCode);
}
