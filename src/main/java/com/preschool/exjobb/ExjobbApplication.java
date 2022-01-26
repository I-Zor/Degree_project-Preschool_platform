package com.preschool.exjobb;

import com.preschool.exjobb.entities.Attendance;
import com.preschool.exjobb.entities.Child;
import com.preschool.exjobb.entities.GroupType;
import com.preschool.exjobb.entities.Weekday;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.repositories.AttendanceRepository;
import com.preschool.exjobb.repositories.ChildRepository;
import com.preschool.exjobb.repositories.GroupTypeRepository;
import com.preschool.exjobb.repositories.WeekdayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ExjobbApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExjobbApplication.class, args);
  }

  @Bean
  public CommandLineRunner bootstrap(GroupTypeRepository groupTypeRepository,
                                     WeekdayRepository weekdayRepository,
                                     AttendanceRepository attendanceRepository,
                                     ChildRepository childRepository){
    return (args -> {

      Weekday monday = new Weekday();
      monday.setWeekday(WeekdayConstant.Måndag);
      Weekday foundMonday = weekdayRepository.findByWeekday(WeekdayConstant.Måndag);
      if (foundMonday == null){
        weekdayRepository.save(monday);
      }
      Weekday tuesday = new Weekday();
      tuesday.setWeekday(WeekdayConstant.Tisdag);
      Weekday foundTuesday = weekdayRepository.findByWeekday(WeekdayConstant.Tisdag);
      if (foundTuesday == null){
        weekdayRepository.save(tuesday);
      }
      Weekday wednesday = new Weekday();
      wednesday.setWeekday(WeekdayConstant.Onsdag);
      Weekday foundWednesday = weekdayRepository.findByWeekday(WeekdayConstant.Onsdag);
      if (foundWednesday == null){
        weekdayRepository.save(wednesday);
      }
      Weekday thursday = new Weekday();
      thursday.setWeekday(WeekdayConstant.Torsdag);
      Weekday foundThursday = weekdayRepository.findByWeekday(WeekdayConstant.Torsdag);
      if (foundThursday == null){
        weekdayRepository.save(thursday);
      }
      Weekday friday = new Weekday();
      friday.setWeekday(WeekdayConstant.Fredag);
      Weekday foundFriday = weekdayRepository.findByWeekday(WeekdayConstant.Fredag);
      if (foundFriday == null){
        weekdayRepository.save(friday);
      }

      GroupType toddler = new GroupType();
      toddler.setGroupConstant(GroupConstant.Småbarn);
      GroupType foundToddler = groupTypeRepository.findByGroupConstant(GroupConstant.Småbarn);
      if (foundToddler == null){
        groupTypeRepository.save(toddler);
      }
      GroupType middleAge = new GroupType();
      middleAge.setGroupConstant(GroupConstant.Medelåldern);
      GroupType foundMiddleAge = groupTypeRepository.findByGroupConstant(GroupConstant.Medelåldern);
      if (foundMiddleAge == null){
        groupTypeRepository.save(middleAge);
      }
      GroupType preschoolAge = new GroupType();
      preschoolAge.setGroupConstant(GroupConstant.Förskoleåldern);
      GroupType foundPreschool = groupTypeRepository.findByGroupConstant(GroupConstant.Förskoleåldern);
      if (foundPreschool == null){
        groupTypeRepository.save(preschoolAge);
      }
      GroupType mix = new GroupType();
      mix.setGroupConstant(GroupConstant.Blandad);
      GroupType foundMix = groupTypeRepository.findByGroupConstant(GroupConstant.Blandad);
      if (foundMix == null){
        groupTypeRepository.save(mix);
      }

      List<Child> allChildren = childRepository.findAll();
      allChildren.forEach(child -> {
        Attendance today = attendanceRepository.findByChildAndDate(child, LocalDate.now());
        if (today == null){
          Attendance attendance = new Attendance();
          attendance.setChild(child);
          attendance.setDate(LocalDate.now());
          attendance.setPresent(true);
          attendanceRepository.save(attendance);
        }
      });
    });
  }
}