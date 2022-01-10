package com.preschool.exjobb.services;

import com.preschool.exjobb.entities.*;
import com.preschool.exjobb.enums.WeekdayConstant;
import com.preschool.exjobb.mappers.CaringTimeMapper;
import com.preschool.exjobb.mappers.ChildMapper;
import com.preschool.exjobb.models.CaringTimeResource;
import com.preschool.exjobb.models.ChildResource;
import com.preschool.exjobb.models.GroupTypeResource;
import com.preschool.exjobb.models.PreschoolGroupResource;
import com.preschool.exjobb.repositories.*;
import com.preschool.exjobb.util.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChildServiceTest {

  @InjectMocks
  private ChildService underTest;
  @Mock
  private ChildRepository childRepository;
  @Mock
  private CityRepository cityRepository;
  @Mock
  private ZipCodeRepository zipCodeRepository;
  @Mock
  private PreschoolGroupRepository preschoolGroupRepository;
  @Mock
  private CaringTimeRepository caringTimeRepository;
  @Mock
  private ChildResource childResource;
  @Mock
  private Child child;
  @Mock
  private PreschoolGroupResource preschoolGroupResource;
  @Mock
  private GroupTypeResource groupTypeResource;
  @Mock
  private CaringTimeResource caringTimeResource;

  @Mock
  private PreschoolGroup preschoolGroup;
  @Mock
  private CaringTime caringTime;
  @Mock
  private PersonalInformation personalInfo;
  @Mock
  private City city;
  @Mock
  private ZipCode zipCode;
  @Mock
  private Caregiver caregiver;

  @Mock
  private ChildMapper childMapper;
  @Mock
  private Converter converter;
  @Mock
  private CaringTimeMapper caringTimeMapper;

  @Test
  void saveChild() {
    //Given
    //ChildResource
    when(groupTypeResource.getGroupType()).thenReturn("preschool_age");
    when(preschoolGroupResource.getGroupType()).thenReturn(groupTypeResource);
    when(childResource.getPreschoolGroup()).thenReturn(preschoolGroupResource);
    when(caringTimeResource.getWeekday()).thenReturn("monday");
    List<CaringTimeResource> caringTimeResources = new ArrayList<>();
    caringTimeResources.add(caringTimeResource);
    when(childResource.getCaringTimes()).thenReturn(caringTimeResources);
    //Child
    when(preschoolGroup.getName()).thenReturn("Nässelfjärilen");
    when(child.getPreschoolGroup()).thenReturn(preschoolGroup);
    when(city.getName()).thenReturn("Södertälje");
    when(personalInfo.getCity()).thenReturn(city);
    when(zipCode.getNumber()).thenReturn("15248");
    when(personalInfo.getZipCode()).thenReturn(zipCode);
    when(child.getPersonalInformation()).thenReturn(personalInfo);
    when(caregiver.getPersonalInformation()).thenReturn(personalInfo);
    List<Caregiver> caregivers = new ArrayList<>();
    caregivers.add(caregiver);
    when(child.getCaregivers()).thenReturn(caregivers);
    when(child.getId()).thenReturn(1L);

    when(childMapper.toChild(childResource)).thenReturn(child);
    when(cityRepository.findByName(any())).thenReturn(Optional.of(city));
    when(zipCodeRepository.findByNumber(any())).thenReturn(Optional.of(zipCode));
    when(preschoolGroupRepository.findByName(any())).thenReturn(Optional.of(preschoolGroup));
    when(childRepository.save(any())).thenReturn(child);

    //When
    Long result = underTest.saveChild(childResource);

    //Then
    verify(childRepository, Mockito.times(1)).save(any());
    verify(cityRepository, times(2)).findByName(any());
    verify(zipCodeRepository, times(2)).findByNumber(any());
    verify(preschoolGroupRepository, times(1)).findByName(any());
    Assertions.assertEquals(result, 1L);
  }

  @Test
  void findChildById() {
    //Given
    when(groupTypeResource.getGroupType()).thenReturn("preschool_age");
    when(preschoolGroupResource.getGroupType()).thenReturn(groupTypeResource);
    when(childResource.getPreschoolGroup()).thenReturn(preschoolGroupResource);
    when(caringTimeResource.getWeekday()).thenReturn("monday");
    List<CaringTimeResource> caringTimeResources = new ArrayList<>();
    caringTimeResources.add(caringTimeResource);
    when(childResource.getCaringTimes()).thenReturn(caringTimeResources);
    when(childRepository.findById(any())).thenReturn(Optional.of(child));
    when(childMapper.toResource(any())).thenReturn(childResource);

    //When
    ChildResource result = underTest.findChildById(1L);

    //Then
    Assertions.assertEquals(result.getPreschoolGroup().getGroupType().getGroupType(), "preschool_age");
    Assertions.assertEquals(result.getCaringTimes().get(0).getWeekday(), "monday");
  }

  @Test
  void findAllChildrenInGroup() {
    //Given
    List<Child> childrenInGroup = new ArrayList<>();
    childrenInGroup.add(child);

    when(preschoolGroupRepository.findById(any())).thenReturn(Optional.of(preschoolGroup));
    when(childRepository.findAllByPreschoolGroup(any())).thenReturn(childrenInGroup);
    when(childMapper.toResource(any())).thenReturn(childResource);

    //When
    List<ChildResource> result = underTest.findAllChildrenInGroup(2L);

    Assertions.assertEquals(result.size(), 1);
  }

  @Test
  void findAllChildren() {
    //Given
    List<Child> childrenInGroup = new ArrayList<>();
    childrenInGroup.add(child);
    when(childRepository.findAll()).thenReturn(childrenInGroup);
    when(childMapper.toResource(any())).thenReturn(childResource);

    //When
    List<ChildResource> result = underTest.findAllChildren();

    //Then
    Assertions.assertEquals(result.size(), 1);
  }

  @Disabled
  @Test
  void upsertCaringTime() {
    //Given
    //ChildResource
    Weekday weekday = new Weekday();
    weekday.setWeekday(WeekdayConstant.monday);

    when(groupTypeResource.getGroupType()).thenReturn("preschool_age");
    when(preschoolGroupResource.getGroupType()).thenReturn(groupTypeResource);
    when(childResource.getPreschoolGroup()).thenReturn(preschoolGroupResource);

    when(caringTimeResource.getWeekday()).thenReturn("monday");
    when(caringTimeResource.getStartHour()).thenReturn(9);
    when(caringTimeResource.getStartMinut()).thenReturn(0);
    when(caringTimeResource.getEndHour()).thenReturn(14);
    when(caringTimeResource.getEndMinut()).thenReturn(0);

    List<CaringTimeResource> caringTimeResources = new ArrayList<>();
    caringTimeResources.add(caringTimeResource);
    when(childResource.getCaringTimes()).thenReturn(caringTimeResources);
    //Child
    when(preschoolGroup.getName()).thenReturn("Nässelfjärilen");
    when(child.getPreschoolGroup()).thenReturn(preschoolGroup);
    when(caringTime.getWeekday()).thenReturn(weekday);
    when(caringTime.getStartHour()).thenReturn(8);
    when(caringTime.getStartMinut()).thenReturn(0);
    when(caringTime.getEndHour()).thenReturn(16);
    when(caringTime.getEndMinut()).thenReturn(0);
    List<CaringTime> caringTimes = new ArrayList<>();
    caringTimes.add(caringTime);
    when(child.getCaringTimes()).thenReturn(caringTimes);
    when(city.getName()).thenReturn("Södertälje");
    when(personalInfo.getCity()).thenReturn(city);
    when(zipCode.getNumber()).thenReturn("15248");
    when(personalInfo.getZipCode()).thenReturn(zipCode);
    when(child.getPersonalInformation()).thenReturn(personalInfo);
    when(caregiver.getPersonalInformation()).thenReturn(personalInfo);
    List<Caregiver> caregivers = new ArrayList<>();
    caregivers.add(caregiver);
    when(child.getCaregivers()).thenReturn(caregivers);
    when(child.getId()).thenReturn(1L);

/*
    CaringTime updated = Mockito.mock(CaringTime.class);
    when(updated.getWeekday()).thenReturn(weekday);
    when(updated.getStartHour()).thenReturn(9);
    when(updated.getStartMinut()).thenReturn(0);
    when(updated.getEndHour()).thenReturn(14);
    when(updated.getEndMinut()).thenReturn(0);
*/
    when(childRepository.findById(any())).thenReturn(Optional.of(child));
    when(converter.toWeekday(any())).thenReturn(weekday);
    when(caringTimeMapper.toCaringTime(any())).thenReturn(caringTime);
  //  when(caringTimeRepository.save(any())).thenReturn(updated);

    //When
    underTest.upsertCaringTime(1L, caringTimeResource);

    //Then
    Assertions.assertEquals(child.getCaringTimes().get(0).getEndHour(), 14);

  }
}