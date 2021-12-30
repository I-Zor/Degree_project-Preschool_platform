package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.AttendanceApi;
import com.preschool.exjobb.models.AttendanceResource;
import com.preschool.exjobb.services.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AttendanceController implements AttendanceApi {

  private final AttendanceService service;

  @Override
  public ResponseEntity<List<AttendanceResource>> getAbsences(Long groupId) {
    List<AttendanceResource> allAbsenceTodayInGroup = service.findAllAbsenceTodayInGroup(groupId);
    return ResponseEntity.ok(allAbsenceTodayInGroup);
  }

  @Override
  public ResponseEntity<List<AttendanceResource>> getPresent(Long groupId) {
    List<AttendanceResource> allPresentTodayInGroup = service.findAllPresentTodayInGroup(groupId);
    return ResponseEntity.ok(allPresentTodayInGroup);
  }

  @Override
  public ResponseEntity<Long> saveAbsence(Long childId, String date) {
    Long absent = service.saveAbsence(childId, date);
    return ResponseEntity.ok(absent);
  }
}