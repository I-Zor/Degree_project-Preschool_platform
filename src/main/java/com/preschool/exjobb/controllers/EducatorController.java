package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.EducatorApi;
import com.preschool.exjobb.models.AttendanceResource;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.models.EducatorResource;
import com.preschool.exjobb.services.AttendanceService;
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
public class EducatorController implements EducatorApi {

  private final EducatorService educatorService;
  private final ChildService childService;
  private final AttendanceService attendanceService;

  @Override
  public ResponseEntity<List<EducatorResource>> getAllEducators() {
    List<EducatorResource> allEducators = educatorService.findAllEducators();
    return ResponseEntity.ok(allEducators);

  }

  @Override
  public ResponseEntity<EducatorResource> getEducator(Long educatorId) {
    EducatorResource child = educatorService.findEducatorById(educatorId);
    return ResponseEntity.ok(child);

  }

  @Override
  public ResponseEntity<ChildResource> educatorGetChild(Long childId) {
    ChildResource child = childService.findChildById(childId);
    return ResponseEntity.ok(child);
  }

  @Override
  public ResponseEntity<List<ChildResource>> educatorGetChildrenInGroup(Long groupId) {
    List<ChildResource> allChildrenInGroup = childService.findAllChildrenInGroup(groupId);
    return ResponseEntity.ok(allChildrenInGroup);
  }

  @Override
  public ResponseEntity<Long> educatorSaveAbsence(Long childId, String date, String reason) {
    Long saved = attendanceService.saveAbsence(childId, date, reason);
    return ResponseEntity.ok(saved);
  }

  @Override
  public ResponseEntity<List<AttendanceResource>> getAbsences(Long groupId) {
    List<AttendanceResource> allAbsenceTodayInGroup = attendanceService.findAllAbsenceTodayInGroup(groupId);
    return ResponseEntity.ok(allAbsenceTodayInGroup);
  }

  @Override
  public ResponseEntity<List<AttendanceResource>> getPresent(Long groupId) {
    List<AttendanceResource> allPresentTodayInGroup = attendanceService.findAllPresentTodayInGroup(groupId);
    return ResponseEntity.ok(allPresentTodayInGroup);
  }

  @Override
  public ResponseEntity<Long> saveEducator(EducatorResource educatorResource) {
    Long id = educatorService.saveEducator(educatorResource);
    return ResponseEntity.ok(id);
  }
}