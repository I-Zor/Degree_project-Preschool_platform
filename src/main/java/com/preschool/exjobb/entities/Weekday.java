package com.preschool.exjobb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Weekday {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private WeekdayConstant name;

  private Weekday(WeekdayConstant name) {
    this.name = name;
  }

  public enum WeekdayConstant {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY
  }

}