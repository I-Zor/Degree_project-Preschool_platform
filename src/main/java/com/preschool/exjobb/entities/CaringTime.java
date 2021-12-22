package com.preschool.exjobb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaringTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private Weekday weekday;

  @Column
  private int startHour;

  @Column
  private int startMinut;

  @Column
  private int endHour;

  @Column
  private int endMinut;


}