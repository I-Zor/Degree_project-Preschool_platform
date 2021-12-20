package com.preschool.exjobb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Educator {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PersonalInformation personalInformation;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private ContactInformation contactInformation;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private Security security;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private PreschoolGroup preschoolGroup;
  @Column
  private boolean isAdmin;


}