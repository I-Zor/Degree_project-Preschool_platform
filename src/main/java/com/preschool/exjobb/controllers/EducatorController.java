package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.EducatorApi;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.models.EducatorResource;
import com.preschool.exjobb.services.EducatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class EducatorController implements EducatorApi {

  private final EducatorService service;

  @Override
  public ResponseEntity<List<EducatorResource>> getAllEducators() {
    List<EducatorResource> allEducators = service.findAllEducators();
    return ResponseEntity.ok(allEducators);

  }

  @Override
  public ResponseEntity<EducatorResource> getEducator(Long educatorId) {
    EducatorResource child = service.findEducatorById(educatorId);
    return ResponseEntity.ok(child);

  }

  @Override
  public ResponseEntity<List<EducatorResource>> getEducatorsInGroup(Long groupId) {
    List<EducatorResource> allEducatorsInGroup = service.findAllEducatorsInGroup(groupId);
    return ResponseEntity.ok(allEducatorsInGroup);
  }

  @Override
  public ResponseEntity<Long> saveEducator(EducatorResource educatorResource) {
    Long id = service.saveEducator(educatorResource);
    return ResponseEntity.ok(id);
  }
}