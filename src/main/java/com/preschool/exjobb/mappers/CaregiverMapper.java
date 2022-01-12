package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Caregiver;
import com.preschool.exjobb.models.CaregiverResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CaregiverMapper {

  CaregiverResource toResource(Caregiver entity);

  @Mapping(target = "role", expression = "java(com.preschool.exjobb.enums.Role.valueOf(resource.getRole()))")
  Caregiver toCaregiver(CaregiverResource resource);
}