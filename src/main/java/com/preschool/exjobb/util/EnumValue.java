package com.preschool.exjobb.util;

import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public class EnumValue {
  public static boolean findEnumValue(Stream<String> enumValueStream, String valueToFind) {
    return nonNull(valueToFind) && enumValueStream.anyMatch(name -> name.equals(valueToFind));
  }
}