package com.petadoption.center.repository;

import com.petadoption.center.enums.Status;
import com.petadoption.center.model.Interest;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, String> {
    Page<Interest> findByOrganizationAndStatusIn(Organization organization, List<Status> statuses, Pageable pageable);
    Page<Interest> findByUserAndStatusIn(User user, List<Status> statuses, Pageable pageable);
}