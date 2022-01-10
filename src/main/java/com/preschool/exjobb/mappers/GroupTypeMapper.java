package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.GroupType;
import com.preschool.exjobb.models.GroupTypeResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupTypeMapper {

  @Mapping(target = "groupType", expression = "java(entity.getGroupConstant().name())")
  GroupTypeResource toGroupTypeResource(GroupType entity);

  @Mapping(target = "groupConstant", expression = "java(com.preschool.exjobb.enums.GroupConstant.valueOf(resource.getGroupType()))")
  GroupType toGroupTypeEntity(GroupTypeResource resource);
}