package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Child;
import com.preschool.exjobb.mappers.ChildMapper;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.repositories.ChildRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChildService {

  private final ChildMapper childMapper;
  private final ChildRepository repository;

  public Long saveChild(ChildResource childResource){

    Child child = childMapper.toChild(childResource);
    Child saved = repository.save(child);
    return saved.getId();
  }
}