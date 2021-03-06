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
public class EducatorService {

  private final EducatorMapper educatorMapper;
  private final CityRepository cityRepository;
  private final ZipCodeRepository zipCodeRepository;
  private final EducatorRepository educatorRepository;
  private final PreschoolGroupRepository preschoolGroupRepository;

  /**
   * Saving Educator
   * @param resource - EducatorResource
   * @return - Educator id
   */
  public Long saveEducator(EducatorResource resource) {
    validateEnum(resource);
    Educator educator = educatorMapper.toEntity(resource);
    Educator checkedEducator = checkInfoAndSave(educator);
    Educator saved = educatorRepository.save(checkedEducator);
    return saved.getId();
  }

  /**
   * Finding all educators in preschool
   * @return - list of EducatorResources
   */
  public List<EducatorResource> findAllEducators(){
    List<Educator> allEducators = educatorRepository.findAll();
    return allEducators.stream()
            .map(educatorMapper::toResource)
            .collect(Collectors.toList());
  }

  /**
   * Finding Educator by id
   * @param id - Educator id, long
   * @return - EducatorResource
   */
  public EducatorResource findEducatorById(long id) {
    Optional<Educator> educator = educatorRepository.findById(id);
    if (educator.isEmpty()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No educator found");
    }
    return educatorMapper.toResource(educator.get());
  }

  /**
   * Finding all educators in the preschool group
   * @param groupId - Preschool group id, long
   * @return - List of EducatorResources
   */
  public List<EducatorResource> findAllEducatorsInGroup(long groupId){
    Optional<PreschoolGroup> preschoolGroup = preschoolGroupRepository.findById(groupId);
    if (preschoolGroup.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No preschool group found");
    }
    List<Educator> educatorsInGroup = educatorRepository.findAllByPreschoolGroup(preschoolGroup.get());
    return educatorsInGroup.stream()
            .map(educatorMapper::toResource)
            .collect(Collectors.toList());
  }

  /**
   * Checking if city, zip code and preschool group already exists in database. If so then it is added to Educator, otherwise
   * a new are saved.
   * @param educator - Educator
   * @return - Educator
   */
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


  /**
   * Validating group type in EducatorResource
   * @param resource - EducatorResource
   */
  private void validateEnum(EducatorResource resource) {
    if (!checkGroup(resource)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  private boolean checkGroup(EducatorResource resource) {
    if (!findEnumValue(Stream.of(GroupConstant.values())
            .map(GroupConstant::name), resource.getPreschoolGroup().getGroupType().getGroupType())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    } else {
      return true;
    }
  }
}