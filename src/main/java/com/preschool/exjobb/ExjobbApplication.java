package com.preschool.exjobb;

import com.preschool.exjobb.entities.GroupType;
import com.preschool.exjobb.entities.Weekday;
import com.preschool.exjobb.enums.GroupConstant;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.repositories.GroupTypeRepository;
import com.preschool.exjobb.repositories.WeekdayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExjobbApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExjobbApplication.class, args);
  }

  @Bean
  public CommandLineRunner bootstrap(GroupTypeRepository groupTypeRepository, WeekdayRepository weekdayRepository){
    return (args -> {

      Weekday monday = new Weekday();
      monday.setWeekday(WeekdayConstant.monday);
      Weekday foundMonday = weekdayRepository.findByWeekday(WeekdayConstant.monday);
      if (foundMonday == null){
        weekdayRepository.save(monday);
      }
      Weekday tuesday = new Weekday();
      tuesday.setWeekday(WeekdayConstant.tuesday);
      Weekday foundTuesday = weekdayRepository.findByWeekday(WeekdayConstant.tuesday);
      if (foundTuesday == null){
        weekdayRepository.save(tuesday);
      }
      Weekday wednesday = new Weekday();
      wednesday.setWeekday(WeekdayConstant.wednesday);
      Weekday foundWednesday = weekdayRepository.findByWeekday(WeekdayConstant.wednesday);
      if (foundWednesday == null){
        weekdayRepository.save(wednesday);
      }
      Weekday thursday = new Weekday();
      thursday.setWeekday(WeekdayConstant.thursday);
      Weekday foundThursday = weekdayRepository.findByWeekday(WeekdayConstant.thursday);
      if (foundThursday == null){
        weekdayRepository.save(thursday);
      }
      Weekday friday = new Weekday();
      friday.setWeekday(WeekdayConstant.friday);
      Weekday foundFriday = weekdayRepository.findByWeekday(WeekdayConstant.friday);
      if (foundFriday == null){
        weekdayRepository.save(friday);
      }

      GroupType toddler = new GroupType();
      toddler.setGroupConstant(GroupConstant.toddler);
      GroupType foundToddler = groupTypeRepository.findByGroupConstant(GroupConstant.toddler);
      if (foundToddler == null){
        groupTypeRepository.save(toddler);
      }
      GroupType middleAge = new GroupType();
      middleAge.setGroupConstant(GroupConstant.middle_age);
      GroupType foundMiddleAge = groupTypeRepository.findByGroupConstant(GroupConstant.middle_age);
      if (foundMiddleAge == null){
        groupTypeRepository.save(middleAge);
      }
      GroupType preschoolAge = new GroupType();
      preschoolAge.setGroupConstant(GroupConstant.preschool_age);
      GroupType foundPreschool = groupTypeRepository.findByGroupConstant(GroupConstant.preschool_age);
      if (foundPreschool == null){
        groupTypeRepository.save(preschoolAge);
      }
      GroupType mix = new GroupType();
      mix.setGroupConstant(GroupConstant.mix_age);
      GroupType foundMix = groupTypeRepository.findByGroupConstant(GroupConstant.mix_age);
      if (foundMix == null){
        groupTypeRepository.save(mix);
      }
    });
  }
}