package com.preschool.exjobb.controllers;

import com.preschool.exjobb.api.LoginApi;
import com.preschool.exjobb.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogInController implements LoginApi {

  private final LoginService service;

  @Override
  public ResponseEntity<Long> logIn(String userName, String password) {
    Long found = service.checkCredentials(userName, password);
    return ResponseEntity.ok(found);
  }
}