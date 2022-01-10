package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Educator;
import com.preschool.exjobb.entities.PreschoolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {

  List<Educator> findAllByPreschoolGroup(PreschoolGroup group);
}