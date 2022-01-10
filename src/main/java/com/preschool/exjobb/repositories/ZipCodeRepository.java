package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {
  Optional<ZipCode> findByNumber(String number);
}