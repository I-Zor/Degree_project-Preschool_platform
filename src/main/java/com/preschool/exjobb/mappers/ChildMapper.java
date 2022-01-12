package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Child;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.util.Converter;
import com.preschool.exjobb.util.CaringTimeMappingHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CaregiverMapper.class)
public abstract class ChildMapper {

  @Autowired
  protected CaringTimeMappingHelper helper;

  @Autowired
  protected Converter converter;

  @Mappings({
          @Mapping(target = "caringTimes", expression = "java(helper.mapCaringTimeList(resource.getCaringTimes()))"),
          @Mapping(target = "preschoolGroup.groupType", expression = "java(converter.toGroupType(preschoolGroupResource.getGroupType().getGroupType()))")
  })
  public abstract Child toChild(ChildResource resource);

  @Mappings({
          @Mapping(target = "caringTimes", expression = "java(helper.mapCaringTimeResources(entity.getCaringTimes()))"),
          @Mapping(target = "preschoolGroup.groupType", expression = "java(converter.toGroupTypeResource(preschoolGroup.getGroupType().getGroupConstant().name()))")
  })
  public abstract ChildResource toResource (Child entity);
}