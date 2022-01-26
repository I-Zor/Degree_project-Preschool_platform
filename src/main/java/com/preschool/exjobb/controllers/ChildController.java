package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.ChildApi;
import com.preschool.exjobb.models.CaringTimeResource;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.services.ChildService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
public class ChildController implements ChildApi {

  private final ChildService service;

  @Override
  public ResponseEntity<Long> saveChild(ChildResource childResource) {
    Long id = service.saveChild(childResource);
    return ResponseEntity.ok(id);
  }

  @Override
  public ResponseEntity<List<ChildResource>> getAllChildren() {
    List<ChildResource> allChildren = service.findAllChildren();
    return ResponseEntity.ok(allChildren);
  }

  /*@Override
  public ResponseEntity<ChildResource> getChild(Long childId) {
    ChildResource child = service.findChildById(childId);
    return ResponseEntity.ok(child);
  }

  @Override
  public ResponseEntity<List<ChildResource>> getChildrenInGroup(Long groupId) {
    List<ChildResource> allChildrenInGroup = service.findAllChildrenInGroup(groupId);
    return ResponseEntity.ok(allChildrenInGroup);
  }

  @Override
  public ResponseEntity<List<ChildResource>> getAllChildren() {
    List<ChildResource> allChildren = service.findAllChildren();
    return ResponseEntity.ok(allChildren);
  }

  @Override
  public ResponseEntity<Long> upsertCaringTime(Long childId, CaringTimeResource caringTimeResource) {
    Long updatedChild = service.upsertCaringTime(childId, caringTimeResource);
    return ResponseEntity.ok(updatedChild);
  }*/
}