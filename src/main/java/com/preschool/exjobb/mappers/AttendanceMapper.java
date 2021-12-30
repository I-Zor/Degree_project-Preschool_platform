package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Attendance;
import com.preschool.exjobb.models.AttendanceResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChildMapper.class})
public interface AttendanceMapper {

  @Mapping(source = "child", target = "child")
  AttendanceResource toResource (Attendance entity);

  @Mapping(source = "child", target = "child")
  Attendance toAttendance (AttendanceResource resource);
}