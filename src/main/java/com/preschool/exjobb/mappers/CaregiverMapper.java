package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Caregiver;
import com.preschool.exjobb.models.CaregiverResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CaregiverMapper {

  CaregiverResource toResource(Caregiver entity);

  Caregiver toCaregiver(CaregiverResource resource);

  List<CaregiverResource> toResourceList(List<Caregiver> entities);
}