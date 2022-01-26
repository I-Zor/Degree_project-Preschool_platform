package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.CaregiverApi;
import com.preschool.exjobb.models.CaregiverResource;
import com.preschool.exjobb.models.CaringTimeResource;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.models.EducatorResource;
import com.preschool.exjobb.services.AttendanceService;
import com.preschool.exjobb.services.CaregiverService;
import com.preschool.exjobb.services.ChildService;
import com.preschool.exjobb.services.EducatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
public class CaregiverController implements CaregiverApi {

  private final ChildService childService;
  private final AttendanceService attendanceService;
  private final EducatorService educatorService;
  private final CaregiverService caregiverService;

  @Override
  public ResponseEntity<List<CaregiverResource>> getAllCaregivers() {
    List<CaregiverResource> allCaregivers = caregiverService.getAllCaregivers();
    return ResponseEntity.ok(allCaregivers);
  }

  @Override
  public ResponseEntity<ChildResource> caregiverGetChild(Long childId) {
    ChildResource child = childService.findChildById(childId);
    return ResponseEntity.ok(child);
  }

  @Override
  public ResponseEntity<Long> caregiverSaveAbsence(Long childId, String date, String reason) {
    Long saved = attendanceService.saveAbsence(childId, date, reason);
    return ResponseEntity.ok(saved);
  }

  @Override
  public ResponseEntity<CaregiverResource> getCaregiver(Long caregiverId) {
    CaregiverResource caregiver = caregiverService.getCaregiver(caregiverId);
    return ResponseEntity.ok(caregiver);
  }

  @Override
  public ResponseEntity<List<EducatorResource>> getEducatorsInGroup(Long groupId) {
    List<EducatorResource> allEducatorsInGroup = educatorService.findAllEducatorsInGroup(groupId);
    return ResponseEntity.ok(allEducatorsInGroup);
  }

  @Override
  public ResponseEntity<Long> upsertCaringTime(Long childId, CaringTimeResource caringTimeResource) {
    Long updated = childService.upsertCaringTime(childId, caringTimeResource);
    return ResponseEntity.ok(updated);
  }
}