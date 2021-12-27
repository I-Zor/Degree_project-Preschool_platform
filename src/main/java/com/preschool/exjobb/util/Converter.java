package com.preschool.exjobb.util;

import com.preschool.exjobb.entities.GroupType;
import com.preschool.exjobb.entities.Weekday;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.mappers.GroupTypeMapper;
import com.preschool.exjobb.mappers.WeekdayMapper;
import com.preschool.exjobb.models.GroupTypeResource;
import com.preschool.exjobb.models.WeekdayResource;
import com.preschool.exjobb.repositories.GroupTypeRepository;
import com.preschool.exjobb.repositories.WeekdayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Converter {

  private final WeekdayRepository weekdayRepository;
  private final GroupTypeRepository groupTypeRepository;
  private final GroupTypeMapper groupTypeMapper;
  private final WeekdayMapper weekdayMapper;

  public Weekday toWeekday(String resource) {
    return weekdayRepository.findByWeekday(WeekdayConstant.valueOf(resource));
  }

  public GroupType toGroupType(String resource) {
    return groupTypeRepository.findByGroupConstant(GroupConstant.valueOf(resource));
  }

  public GroupTypeResource toGroupTypeResource(String entity) {
    GroupType groupType = groupTypeRepository.findByGroupConstant(GroupConstant.valueOf(entity));
    return groupTypeMapper.toGroupTypeResource(groupType);
  }

  public WeekdayResource toWeekdayResource(String entity){
    Weekday weekday = weekdayRepository.findByWeekday(WeekdayConstant.valueOf(entity));
    return weekdayMapper.toWeekdayResource(weekday);
  }

}