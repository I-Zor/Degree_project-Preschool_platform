package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.PreschoolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreschoolGroupRepository extends JpaRepository<PreschoolGroup, Long> {
}