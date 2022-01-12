package com.preschool.exjobb.entities;

import com.preschool.exjobb.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caregiver {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private PersonalInformation personalInformation;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private ContactInformation contactInformation;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private Security security;

  @Column
  @Enumerated(EnumType.STRING)
  private Role role = Role.caregiver;
}