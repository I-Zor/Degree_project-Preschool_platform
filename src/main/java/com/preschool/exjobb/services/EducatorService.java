package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Educator;
import com.preschool.exjobb.entities.PersonalInformation;
import com.preschool.exjobb.entities.PreschoolGroup;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.mappers.EducatorMapper;
import com.preschool.exjobb.models.EducatorResource;
import com.preschool.exjobb.repositories.CityRepository;
import com.preschool.exjobb.repositories.EducatorRepository;
import com.preschool.exjobb.repositories.PreschoolGroupRepository;
import com.preschool.exjobb.repositories.ZipCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.preschool.exjobb.util.EnumValue.findEnumValue;

@AllArgsConstructor
@Service
public class EducatorService {

  private final EducatorMapper educatorMapper;
  private final CityRepository cityRepository;
  private final ZipCodeRepository zipCodeRepository;
  private final EducatorRepository educatorRepository;
  private final PreschoolGroupRepository preschoolGroupRepository;

  public Long saveEducator(EducatorResource resource) {
    validateEnum(resource);
    Educator educator = educatorMapper.toEntity(resource);
    Educator checkedEducator = checkInfoAndSave(educator);
    Educator saved = educatorRepository.save(checkedEducator);
    return saved.getId();
  }

  public List<EducatorResource> findAllEducators(){
    List<Educator> allEducators = educatorRepository.findAll();
    return allEducators.stream()
            .map(educatorMapper::toResource)
            .collect(Collectors.toList());
  }

  public EducatorResource findEducatorById(long id) {
    Educator educator = educatorRepository.findById(id).orElse(null);
    return educatorMapper.toResource(educator);
  }

  public List<EducatorResource> findAllEducatorsInGroup(long groupId){
    PreschoolGroup preschoolGroup = preschoolGroupRepository.findById(groupId).orElse(null);
    List<Educator> educatorsInGroup = educatorRepository.findAllByPreschoolGroup(preschoolGroup);
    return educatorsInGroup.stream()
            .map(educatorMapper::toResource)
            .collect(Collectors.toList());
  }

  private Educator checkInfoAndSave(Educator educator) {
    PersonalInformation personalInformation = educator.getPersonalInformation();
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

    preschoolGroupRepository.findByName(educator.getPreschoolGroup().getName()).ifPresentOrElse(
            educator::setPreschoolGroup, () -> {
              preschoolGroupRepository.save(educator.getPreschoolGroup());
            }
    );
    return educator;
  }


  private void validateEnum(EducatorResource resource) {
    if (!checkGroup(resource)) {
      throw new IllegalArgumentException();
    }
  }

  private boolean checkGroup(EducatorResource resource) {
    if (!findEnumValue(Stream.of(GroupConstant.values())
            .map(GroupConstant::name), resource.getPreschoolGroup().getGroupType().getGroupType())) {
      throw new IllegalArgumentException();
    } else {
      return true;
    }
  }
}