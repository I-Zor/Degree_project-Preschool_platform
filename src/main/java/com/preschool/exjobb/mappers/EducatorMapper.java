package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Educator;
import com.preschool.exjobb.models.EducatorResource;
import com.preschool.exjobb.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EducatorMapper {

  @Autowired
  protected Converter converter;

  @Mappings({
          @Mapping(target = "preschoolGroup.groupType", expression = "java(converter.toGroupTypeResource(preschoolGroup.getGroupType().getGroupConstant().name()))"),
          @Mapping(target = "isAdmin", source = "admin")
  })
  public abstract EducatorResource toResource(Educator entity);

  @Mappings({
          @Mapping(target = "preschoolGroup.groupType", expression = "java(converter.toGroupType(preschoolGroupResource.getGroupType().getGroupType()))"),
          @Mapping(target = "admin", source = "isAdmin")
  })
  public abstract Educator toEntity(EducatorResource resource);
}