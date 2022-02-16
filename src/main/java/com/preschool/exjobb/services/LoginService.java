package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Caregiver;
import com.preschool.exjobb.entities.Educator;
import com.preschool.exjobb.entities.Security;
import com.preschool.exjobb.repositories.CaregiverRepository;
import com.preschool.exjobb.repositories.EducatorRepository;
import com.preschool.exjobb.repositories.SecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

  private final EducatorRepository educatorRepository;
  private final CaregiverRepository caregiverRepository;
  private final SecurityRepository securityRepository;

  /**
   * Checking if username and password exist in database
   * @param userName - username, String
   * @param password - password, String
   * @return - user's id (Educator's or Caregiver's)
   */
  public Long checkCredentials(String userName, String password){

    Optional<Security> found = securityRepository.findByUserNameAndPassword(userName, password);
    if (found.isPresent()){
      return findUser(found.get());
    }
    else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
    }
  }

  /**
   * Finding user by Security
   * @param security - Security
   * @return - user's id
   */
  private Long findUser(Security security) {
    Optional<Educator> foundEducator = educatorRepository.findBySecurity(security);
    Optional<Caregiver> foundCaregiver = caregiverRepository.findBySecurity(security);
    if (foundEducator.isPresent()){
      return foundEducator.get().getId();
    }
    else if(foundCaregiver.isPresent()){
      return foundCaregiver.get().getId();
    }
    else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No security found");
    }
  }
}