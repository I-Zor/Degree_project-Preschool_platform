package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Weekday;
import com.preschool.exjobb.enums.WeekdayConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekdayRepository extends JpaRepository<Weekday, Long> {

  Weekday findByWeekday(WeekdayConstant weekday);
}