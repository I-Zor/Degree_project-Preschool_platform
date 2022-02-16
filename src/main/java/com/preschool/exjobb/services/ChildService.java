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
  private final CaregiverRepository caregiverRepository;

  /**
   * Saving Child object
   *
   * @param childResource - ChildResource
   * @return - saved Child id, long
   */
  public Long saveChild(ChildResource childResource) {
    validateEnums(childResource);
    Child child = childMapper.toChild(childResource);
    return checkInfoAndSave(child);
  }

  /**
   * Adding Caregiver to Child object
   *
   * @param caregiversId - Caregiver id, long
   * @param childId      - Child id, long
   * @return - Child id
   */
  public Long saveChildToCaregiver(long caregiversId, long childId) {
    Optional<Child> child = childRepository.findById(childId);
    if (child.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No child found");
    }
    Caregiver caregiver = findCaregiver(caregiversId);
    child.get().addCaregiver(caregiver);
    Child saved = childRepository.save(child.get());
    return saved.getId();
  }

  /**
   * Finding Caregiver by id
   *
   * @param caregiversId - Caregiver id, long
   * @return - Caregiver
   */
  private Caregiver findCaregiver(long caregiversId) {
    Optional<Caregiver> foundCaregiver = caregiverRepository.findById(caregiversId);
    if (foundCaregiver.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No caregiver found");
    }
    return foundCaregiver.get();
  }

  /**
   * Finding Child by id
   *
   * @param id - Child id, long
   * @return - ChildResource
   */
  public ChildResource findChildById(long id) {
    Optional<Child> child = childRepository.findById(id);
    if (child.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No child found");
    }
    return childMapper.toResource(child.get());
  }

  /**
   * Finding all children in certain preschool group
   *
   * @param groupId - Preschool group id
   * @return - list of ChildResources
   */
  public List<ChildResource> findAllChildrenInGroup(long groupId) {
    Optional<PreschoolGroup> preschoolGroup = preschoolGroupRepository.findById(groupId);
    if (preschoolGroup.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No preschool group found");
    }
    List<Child> childrenInGroup = childRepository.findAllByPreschoolGroup(preschoolGroup.get());
    return childrenInGroup.stream()
            .map(childMapper::toResource)
            .collect(Collectors.toList());

  }

  /**
   * Finding all Children in preschool
   *
   * @return - list of ChildResources
   */
  public List<ChildResource> findAllChildren() {
    List<Child> allChildren = childRepository.findAll();
    return allChildren.stream()
            .map(childMapper::toResource)
            .collect(Collectors.toList());
  }

  /**
   * Finding all children of certain caregiver
   *
   * @param caregiverId - Caregiver id, long
   * @return - list of ChildResources
   */
  public List<ChildResource> findAllChildrenByCaregiver(long caregiverId) {
    List<Child> byCaregiver = childRepository.findByCaregiver(caregiverId);
    if (byCaregiver.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No children found");
    }
    return byCaregiver.stream()
            .map(childMapper::toResource)
            .collect(Collectors.toList());
  }

  /**
   * Adding new caring time to certain day of the week or is updating already existing one
   *
   * @param childId       - Child id, long
   * @param newCaringTime - CaringTimeResource
   * @return - Child id, long
   */
  public Long upsertCaringTime(long childId, CaringTimeResource newCaringTime) {
    Optional<Child> child = childRepository.findById(childId);
    if (child.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No child found");
    }
    List<CaringTime> presentCaringTimes = child.get().getCaringTimes();

    Optional<CaringTime> found = presentCaringTimes.stream()
            .filter(caringTime -> caringTime.getWeekday()
                    .equals(converter.toWeekday(newCaringTime.getWeekday())))
            .findFirst();
    if (found.isPresent()) {
      updateFound(found, newCaringTime);
      return found.get().getId();
    } else {
      CaringTime caringTimeToSave = caringTimeMapper.toCaringTime(newCaringTime);
      child.get().getCaringTimes().add(caringTimeToSave);
      childRepository.save(child.get());
      return caringTimeToSave.getId();
    }
  }

  /**
   * Updating already existing caring time
   *
   * @param found         - Optional of CaringTime
   * @param newCaringTime - CaringTimeResource
   */
  private void updateFound(Optional<CaringTime> found, CaringTimeResource newCaringTime) {
    CaringTime foundCaringTime = found.get();
    foundCaringTime.setWeekday(converter.toWeekday(newCaringTime.getWeekday()));
    foundCaringTime.setStartHour(newCaringTime.getStartHour());
    foundCaringTime.setEndHour(newCaringTime.getEndHour());
    foundCaringTime.setEndMinut(newCaringTime.getEndMinut());
    foundCaringTime.setStartMinut(newCaringTime.getStartMinut());
    caringTimeRepository.save(foundCaringTime);
  }

  /**
   * Checking if weekday and group attributes in ChildResource matches enums in database
   *
   * @param resource - ChildResource
   */
  private void validateEnums(ChildResource resource) {
    if (!checkWeekday(resource) || !checkGroup(resource)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Validating group type in ChildResource
   *
   * @param resource - ChildResource
   * @return - boolean
   */
  private boolean checkGroup(ChildResource resource) {
    if (!findEnumValue(Stream.of(GroupConstant.values())
            .map(GroupConstant::name), resource.getPreschoolGroup().getGroupType().getGroupType())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    } else {
      return true;
    }
  }

  /**
   * Validating weekday in ChildResource
   *
   * @param resource - ChildResource
   * @return - boolean
   */
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

  /**
   * Checking if preschool group in Child object already exists in database. If so then it is added to Child, otherwise
   * a new preschool group is saved.
   * Checking if city and zip code in personal information already exists. If so then they are set to personal information to both Child
   * and Caregiver, otherwise new are saved.
   *
   * @param child - Child id, long
   * @return - Child id, long
   */
  public Long checkInfoAndSave(Child child) {
    checkPersonalInfoAndSave(child.getPersonalInformation());

    List<Caregiver> caregivers = child.getCaregivers();
    if (caregivers != null) {
      caregivers.forEach(caregiver -> {
        checkPersonalInfoAndSave(caregiver.getPersonalInformation());
      });
    }

    preschoolGroupRepository.findByName(child.getPreschoolGroup().getName()).ifPresentOrElse(
            child::setPreschoolGroup, () -> {
              preschoolGroupRepository.save(child.getPreschoolGroup());
            }
    );
    Child saved = childRepository.save(child);
    return saved.getId();
  }

  /**
   * Checking city and zip code in personal information
   *
   * @param personalInformation - PersonalInformation
   */
  public void checkPersonalInfoAndSave(PersonalInformation personalInformation) {

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