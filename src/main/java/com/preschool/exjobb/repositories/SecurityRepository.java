package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

  Optional<Security> findByUserNameAndPassword(String userName, String password);
}