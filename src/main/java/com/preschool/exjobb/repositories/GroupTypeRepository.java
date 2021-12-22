package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.GroupType;
import com.preschool.exjobb.enums.GroupConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {

  GroupType findByGroupConstant(GroupConstant groupConstant);
}