package com.preschool.exjobb.util;

import com.preschool.exjobb.entities.CaringTime;
import com.preschool.exjobb.mappers.CaringTimeMapper;
import com.preschool.exjobb.models.CaringTimeResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CaringTimeMappingHelper {

  private final CaringTimeMapper caringTimeMapper;

  public List<CaringTime> mapCaringTimeList(List<CaringTimeResource> resources){
    return resources.stream().map(caringTimeMapper::toCaringTime).collect(Collectors.toList());
  }
}