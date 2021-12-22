package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.ChildApi;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.services.ChildService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ChildController implements ChildApi {

  private final ChildService service;

  @Override
  public ResponseEntity<Long> saveChild(ChildResource childResource) {
    Long id = service.saveChild(childResource);
    return ResponseEntity.ok(id);
  }
}