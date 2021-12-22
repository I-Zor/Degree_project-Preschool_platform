package com.preschool.exjobb.util;

import com.preschool.exjobb.entities.GroupType;
import com.preschool.exjobb.entities.Weekday;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.repositories.GroupTypeRepository;
import com.preschool.exjobb.repositories.WeekdayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Converter {

  private final WeekdayRepository weekdayRepository;
  private final GroupTypeRepository groupTypeRepository;

  public Weekday toWeekday(String resource){
    return weekdayRepository.findByWeekday(WeekdayConstant.valueOf(resource));
  }

  public GroupType toGroupType(String resource){
    return groupTypeRepository.findByGroupConstant(GroupConstant.valueOf(resource));
  }

}