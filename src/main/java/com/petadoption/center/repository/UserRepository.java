package com.petadoption.center.repository;

import com.petadoption.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEMail(String email);

    Optional<User> findByPhoneNumber(Integer phoneNumber);
}
