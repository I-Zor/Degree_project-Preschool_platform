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

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PersonalInformation personalInformation;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private PreschoolGroup preschoolGroup;

  @ManyToMany
  @JoinTable(
          //name = "child_caregiver",
          joinColumns = {@JoinColumn(referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")}
  )
  private List<Caregiver> caregivers;

  @ManyToMany
  @JoinTable(
          //name = "child_caring_time",
          joinColumns = {@JoinColumn(referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")}
  )
  private List<CaringTime> caringTimes;

  @ManyToMany
  @JoinTable(
          //name = "child_relative",
          joinColumns = {@JoinColumn(referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")} 
  )
  private List<Relative> relatives;

}