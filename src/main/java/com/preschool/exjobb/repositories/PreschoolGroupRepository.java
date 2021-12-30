package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.PreschoolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreschoolGroupRepository extends JpaRepository<PreschoolGroup, Long> {

  Optional<PreschoolGroup> findByName(String name);
}