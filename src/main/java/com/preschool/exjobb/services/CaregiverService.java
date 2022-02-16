package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Caregiver;
import com.preschool.exjobb.mappers.CaregiverMapper;
import com.preschool.exjobb.models.CaregiverResource;
import com.preschool.exjobb.repositories.CaregiverRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CaregiverService {

  private final CaregiverRepository caregiverRepository;
  private final CaregiverMapper caregiverMapper;

  /**
   * Finding Caregiver by id
   *
   * @param id - Caregiver id, long
   * @return - CaregiverResource
   */
  public CaregiverResource getCaregiver(long id) {
    Optional<Caregiver> caregiver = caregiverRepository.findById(id);
    if (caregiver.isEmpty()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No caregiver found");
    } else {
      return caregiverMapper.toResource(caregiver.get());
    }
  }

  /**
   * Getting all Caregivers in preschool
   *
   * @return - list of CaregiverResources
   */
  public List<CaregiverResource> getAllCaregivers() {
    List<Caregiver> allCaregivers = caregiverRepository.findAll();
    return caregiverMapper.toResourceList(allCaregivers);
  }
}