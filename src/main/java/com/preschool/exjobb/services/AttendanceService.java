package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.Attendance;
import com.preschool.exjobb.entities.Child;
import com.preschool.exjobb.entities.PreschoolGroup;
import com.preschool.exjobb.mappers.AttendanceMapper;
import com.preschool.exjobb.models.AttendanceResource;
import com.preschool.exjobb.repositories.AttendanceRepository;
import com.preschool.exjobb.repositories.ChildRepository;
import com.preschool.exjobb.repositories.PreschoolGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttendanceService {

  public final AttendanceMapper attendanceMapper;
  public final ChildRepository childRepository;
  public final AttendanceRepository attendanceRepository;
  public final PreschoolGroupRepository preschoolGroupRepository;

  public Long saveAbsence(long childId, String inputDate, String reasonToAbsence) {
    Child child = childRepository.findById(childId).get();
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    formatter = formatter.withLocale(new Locale("sv", "SE"));
    LocalDate date = LocalDate.parse(inputDate, formatter);
    Attendance foundByChildAndDate = attendanceRepository.findByChildAndDate(child, date);
    if (foundByChildAndDate.isPresent()){
      foundByChildAndDate.setPresent(false);
      foundByChildAndDate.setReasonToAbsence(reasonToAbsence);
      attendanceRepository.save(foundByChildAndDate);
      return foundByChildAndDate.getId();
    }
    else {
      Attendance newAttendance = new Attendance();
      newAttendance.setChild(child);
      newAttendance.setDate(date);
      newAttendance.setPresent(false);
      newAttendance.setReasonToAbsence(reasonToAbsence);
      Attendance saved = attendanceRepository.save(newAttendance);
      return saved.getId();
    }
  }

  public List<AttendanceResource> findAllAbsenceTodayInGroup(long groupId){
    PreschoolGroup preschoolGroup = preschoolGroupRepository.findById(groupId).get();
    List<Attendance> allAttendancesToday = attendanceRepository.findAllByDate(LocalDate.now());

    List<Attendance> allAbsencesTodayInGroup = allAttendancesToday.stream()
            .filter(attendance -> attendance.getChild().getPreschoolGroup().equals(preschoolGroup) && !attendance.isPresent())
            .collect(Collectors.toList());

    return allAbsencesTodayInGroup.stream().map(attendanceMapper::toResource).collect(Collectors.toList());
    }

    public List<AttendanceResource> findAllPresentTodayInGroup(long groupId){
      PreschoolGroup preschoolGroup = preschoolGroupRepository.findById(groupId).get();
      List<Attendance> allAttendancesToday = attendanceRepository.findAllByDate(LocalDate.now());

      List<Attendance> allPresentTodayInGroup = allAttendancesToday.stream()
              .filter(attendance -> attendance.getChild().getPreschoolGroup().equals(preschoolGroup) && attendance.isPresent())
              .collect(Collectors.toList());

      return allPresentTodayInGroup.stream().map(attendanceMapper::toResource).collect(Collectors.toList());
    }
}