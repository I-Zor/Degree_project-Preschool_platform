package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.ChildApi;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.services.ChildService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ChildController implements ChildApi {

  private final ChildService service;

  @Override
  public ResponseEntity<Long> saveChild(ChildResource childResource) {
    Long id = service.saveChild(childResource);
    return ResponseEntity.ok(id);
  }

  @Override
  public ResponseEntity<ChildResource> getChild(String childId) {
    ChildResource child = service.findChildById(childId);
    return ResponseEntity.ok(child);
  }

  @Override
  public ResponseEntity<List<ChildResource>> getAllChildren() {
    return ChildApi.super.getAllChildren();
  }
}