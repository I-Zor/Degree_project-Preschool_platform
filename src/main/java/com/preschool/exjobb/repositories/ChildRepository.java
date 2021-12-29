package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Child;
import com.preschool.exjobb.entities.PreschoolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

  List<Child> findAllByPreschoolGroup(PreschoolGroup group);

}