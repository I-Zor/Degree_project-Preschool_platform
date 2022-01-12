package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.*;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.mappers.CaringTimeMapper;
import com.preschool.exjobb.mappers.ChildMapper;
import com.preschool.exjobb.models.CaringTimeResource;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.repositories.*;
import com.preschool.exjobb.util.Converter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.preschool.exjobb.util.EnumValue.findEnumValue;

@AllArgsConstructor
@Service
public class ChildService {

  private final ChildMapper childMapper;
  private final ChildRepository childRepository;
  private final PreschoolGroupRepository preschoolGroupRepository;
  private final CityRepository cityRepository;
  private final ZipCodeRepository zipCodeRepository;
  private final CaringTimeRepository caringTimeRepository;
  private final Converter converter;
  private final CaringTimeMapper caringTimeMapper;

  public Long saveChild(ChildResource childResource) {

    validateEnums(childResource);
    Child child = childMapper.toChild(childResource);
    return checkInfoAndSave(child);
  }

  public ChildResource findChildById(long id) {
    Child child = childRepository.findById(id).orElse(null);
    return childMapper.toResource(child);
  }

  public List<ChildResource> findAllChildrenInGroup(long groupId) {
    PreschoolGroup preschoolGroup = preschoolGroupRepository.findById(groupId).orElse(null);
    List<Child> childrenInGroup = childRepository.findAllByPreschoolGroup(preschoolGroup);
    return childrenInGroup.stream().map(childMapper::toResource).collect(Collectors.toList());
  }

  public List<ChildResource> findAllChildren() {
    List<Child> allChildren = childRepository.findAll();
    return allChildren.stream().map(childMapper::toResource).collect(Collectors.toList());
  }

  public Long upsertCaringTime(long childId, CaringTimeResource newCaringTime) {
    Child child = childRepository.findById(childId).get();
    List<CaringTime> presentCaringTimes = child.getCaringTimes();
    Optional<CaringTime> found = presentCaringTimes.stream()
            .filter(caringTime -> caringTime.getWeekday()
                    .equals(converter.toWeekday(newCaringTime.getWeekday())))
            .findFirst();
    if (found.isPresent()){
      updateFound(found, newCaringTime);
    }
    else {
      child.getCaringTimes().add(caringTimeMapper.toCaringTime(newCaringTime));
      childRepository.save(child);
    }
    return childId;
  }

  private void updateFound(Optional<CaringTime> found, CaringTimeResource newCaringTime) {
    CaringTime foundCaringTime = found.get();
    foundCaringTime.setWeekday(converter.toWeekday(newCaringTime.getWeekday()));
    foundCaringTime.setStartHour(newCaringTime.getStartHour());
    foundCaringTime.setEndHour(newCaringTime.getEndHour());
    foundCaringTime.setEndMinut(newCaringTime.getEndMinut());
    foundCaringTime.setStartMinut(newCaringTime.getStartMinut());
    caringTimeRepository.save(foundCaringTime);
  }

  private void validateEnums(ChildResource resource) {

    if (!checkWeekday(resource) || !checkGroup(resource)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  private boolean checkGroup(ChildResource resource) {
    if (!findEnumValue(Stream.of(GroupConstant.values())
            .map(GroupConstant::name), resource.getPreschoolGroup().getGroupType().getGroupType())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    } else {
      return true;
    }
  }

  private boolean checkWeekday(ChildResource resource) {
    List<String> days = resource.getCaringTimes().stream()
            .map(CaringTimeResource::getWeekday)
            .collect(Collectors.toList());
    days.forEach(constant -> {
      if (!findEnumValue(Stream.of(WeekdayConstant.values())
              .map(WeekdayConstant::name), constant)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
    });
    return true;
  }

  public Long checkInfoAndSave(Child child) {

    checkPersonalInfoAndSave(child.getPersonalInformation());

    List<Caregiver> caregivers = child.getCaregivers();
    caregivers.forEach(caregiver -> {
      checkPersonalInfoAndSave(caregiver.getPersonalInformation());
    });

    preschoolGroupRepository.findByName(child.getPreschoolGroup().getName()).ifPresentOrElse(
            child::setPreschoolGroup, () -> {
              preschoolGroupRepository.save(child.getPreschoolGroup());
            }
    );
    Child saved = childRepository.save(child);
    return saved.getId();
  }

  private void checkPersonalInfoAndSave(PersonalInformation personalInformation) {

    cityRepository.findByName(personalInformation.getCity().getName()).ifPresentOrElse(
            personalInformation::setCity, () -> {
              cityRepository.save(personalInformation.getCity());
            }
    );

    zipCodeRepository.findByNumber(personalInformation.getZipCode().getNumber()).ifPresentOrElse(
            personalInformation::setZipCode, () -> {
              zipCodeRepository.save(personalInformation.getZipCode());
            }
    );
  }
}