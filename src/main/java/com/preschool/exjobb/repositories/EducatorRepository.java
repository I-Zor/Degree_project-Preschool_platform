package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Educator;
import com.preschool.exjobb.entities.PreschoolGroup;
import com.preschool.exjobb.entities.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {

  List<Educator> findAllByPreschoolGroup(PreschoolGroup group);

  Optional<Educator> findBySecurity(Security security);
}