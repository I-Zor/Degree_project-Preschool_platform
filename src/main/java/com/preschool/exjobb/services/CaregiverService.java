package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Caregiver;
import com.preschool.exjobb.mappers.CaregiverMapper;
import com.preschool.exjobb.models.CaregiverResource;
import com.preschool.exjobb.repositories.CaregiverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    Caregiver caregiver = caregiverRepository.findById(id).get();
    return caregiverMapper.toResource(caregiver);
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