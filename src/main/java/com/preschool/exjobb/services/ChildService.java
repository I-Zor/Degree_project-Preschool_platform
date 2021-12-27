package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Child;
import com.preschool.exjobb.entities.PreschoolGroup;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.mappers.ChildMapper;
import com.preschool.exjobb.models.CaringTimeResource;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.repositories.ChildRepository;
import com.preschool.exjobb.repositories.PreschoolGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.preschool.exjobb.util.EnumValue.findEnumValue;

@AllArgsConstructor
@Service
public class ChildService {

  private final ChildMapper childMapper;
  private final ChildRepository childRepository;
  private final PreschoolGroupRepository preschoolGroupRepository;
  //private final PersonalInformationRepository personalInformationRepository;

  public Long saveChild(ChildResource childResource){

    validateEnums(childResource);

    Child child = childMapper.toChild(childResource);
    Child saved = childRepository.save(child);
    return saved.getId();
  }

  public ChildResource findChildById(String id){
    long parsedId = Long.parseLong(id);
    Child child = childRepository.findById(parsedId).orElse(null);
    return childMapper.toResource(child);
  }

  public List<ChildResource> findAllChildrenInGroup(String groupId){
    long parsedId = Long.parseLong(groupId);
    PreschoolGroup preschoolGroup = preschoolGroupRepository.findById(parsedId).orElse(null);
    List<Child> childrenInGroup = childRepository.findAllByPreschoolGroup(preschoolGroup);
    return  childrenInGroup.stream().map(childMapper::toResource).collect(Collectors.toList());
  }

/*
  public void addCaregiverToChild(Caregiver caregiver, Child child){
    checkPersonalNumber(caregiver);
    child.getCaregivers().add(caregiver);
  }

  private void checkPersonalNumber(Caregiver caregiver) {
    PersonalInformation personalNumber = personalInformationRepository.findByPersonalNumber(caregiver.getPersonalInformation().getPersonalNumber());
    if (personalNumber != null){
      throw new IllegalArgumentException();
    }
  }
*/

  private void validateEnums(ChildResource resource) {

    if (!checkWeekday(resource) || !checkGroup(resource)){
      throw new IllegalArgumentException();
    }
  }

  private boolean checkGroup(ChildResource resource) {
    if (!findEnumValue(Stream.of(GroupConstant.values())
            .map(GroupConstant::name), resource.getPreschoolGroup().getGroupType().getGroupType())){
      throw new IllegalArgumentException();
    }
    else {
      return true;
    }
  }

  private boolean checkWeekday(ChildResource resource) {
    List<String> days = resource.getCaringTimes().stream()
            .map(CaringTimeResource::getWeekday)
            .collect(Collectors.toList());
    days.forEach(constant -> {
      if (!findEnumValue(Stream.of(WeekdayConstant.values())
              .map(WeekdayConstant::name), constant)){
        throw new IllegalArgumentException();
      }
    });
    return true;
  }
}