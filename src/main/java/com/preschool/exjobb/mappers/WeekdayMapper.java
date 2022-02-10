package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Weekday;
import com.preschool.exjobb.models.WeekdayResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeekdayMapper {

  @Mapping(target = "weekday", expression = "java(entity.getWeekday().name())")
  WeekdayResource toWeekdayResource (Weekday entity);
}