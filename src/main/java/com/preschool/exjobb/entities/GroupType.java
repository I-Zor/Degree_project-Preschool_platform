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
public class GroupType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private GroupConstant name;

  private GroupType(GroupConstant name){
    this.name = name;
  }

  public enum GroupConstant {
    TODDLER,
    MIDDLE_AGE,
    PRESCHOOL_AGE,
    MIX_AGE
  }
}