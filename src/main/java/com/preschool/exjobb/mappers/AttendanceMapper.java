package com.preschool.exjobb.mappers;

import com.preschool.exjobb.entities.Attendance;
import com.preschool.exjobb.models.AttendanceResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ChildMapper.class})
public interface AttendanceMapper {

  @Mappings({
          @Mapping(source = "child", target = "child"),
          @Mapping(source = "present", target = "isPresent")
  })
  AttendanceResource toResource (Attendance entity);

  @Mappings({
          @Mapping(source = "child", target = "child"),
          @Mapping(source = "isPresent", target = "present")
  })
  Attendance toAttendance (AttendanceResource resource);
}