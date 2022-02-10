package com.preschool.exjobb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column(unique = true)
  private String personalNumber;

  @Column
  private String address;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private ZipCode zipCode;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private City city;
}