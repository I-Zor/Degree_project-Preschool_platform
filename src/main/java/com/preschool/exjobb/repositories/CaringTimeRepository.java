package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.CaringTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaringTimeRepository extends JpaRepository<CaringTime, Long> {
}