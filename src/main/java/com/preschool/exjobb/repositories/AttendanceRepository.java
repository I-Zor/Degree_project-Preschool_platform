package com.preschool.exjobb.repositories;

import com.preschool.exjobb.entities.Attendance;
import com.preschool.exjobb.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  Attendance findByChildAndDate(Child child, LocalDate date);
  List<Attendance> findAllByDate(LocalDate date);
}