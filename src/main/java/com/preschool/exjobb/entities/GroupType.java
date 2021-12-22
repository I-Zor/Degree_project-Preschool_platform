package com.preschool.exjobb.entities;

import com.preschool.exjobb.enums.GroupConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  @Enumerated(EnumType.STRING)
  private GroupConstant groupConstant;

}