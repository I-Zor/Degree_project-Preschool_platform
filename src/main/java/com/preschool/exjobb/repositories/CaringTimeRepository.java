package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.CaringTime;
import com.preschool.exjobb.entities.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaringTimeRepository extends JpaRepository<CaringTime, Long> {
  Optional<CaringTime> findByWeekday(Weekday day);
}