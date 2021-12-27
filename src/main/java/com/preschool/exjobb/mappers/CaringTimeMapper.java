package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.CaringTime;
import com.preschool.exjobb.models.CaringTimeResource;
import com.preschool.exjobb.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CaringTimeMapper {

  @Autowired
  protected Converter converter;

  @Mapping(target = "weekday", expression = "java(converter.toWeekday(resource.getWeekday()))")
  public abstract CaringTime toCaringTime(CaringTimeResource resource);

  @Mapping(target = "weekday", expression = "java(entity.getWeekday().getWeekday().name())")
  public abstract CaringTimeResource toCaringTimeResources(CaringTime entity);
}