package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Caregiver;
import com.preschool.exjobb.entities.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

  Optional<Caregiver> findBySecurity(Security security);
}