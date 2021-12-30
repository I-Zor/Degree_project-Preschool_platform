package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Educator;
import com.preschool.exjobb.models.EducatorResource;
import com.preschool.exjobb.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EducatorMapper {

  @Autowired
  protected Converter converter;

  @Mapping(target = "preschoolGroup.groupType", expression = "java(converter.toGroupTypeResource(preschoolGroup.getGroupType().getGroupConstant().name()))")
  public abstract EducatorResource toResource(Educator entity);

  @Mapping(target = "preschoolGroup.groupType", expression = "java(converter.toGroupType(preschoolGroupResource.getGroupType().getGroupType()))")
  public abstract Educator toEntity(EducatorResource resource);
}