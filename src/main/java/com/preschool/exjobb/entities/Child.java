package com.preschool.exjobb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Child {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private PersonalInformation personalInformation;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private PreschoolGroup preschoolGroup;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          joinColumns = {@JoinColumn(referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")}
  )
  private List<Caregiver> caregivers;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          joinColumns = {@JoinColumn(referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")}
  )
  private List<CaringTime> caringTimes;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          joinColumns = {@JoinColumn(referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")} 
  )
  private List<Relative> relatives;


}