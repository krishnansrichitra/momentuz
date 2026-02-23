package com.momentus.foundation.common;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

  public static String ConvertToString(Object obj) {
    if (obj == null) return null;
    else return String.valueOf(obj);
  }

  public static Set<String> splitCSV(String csvValue) {
    Set<String> csvSet =
        Arrays.stream(csvValue.split(","))
            .map(String::trim) // remove spaces
            .filter(s -> !s.isEmpty()) // ignore empty values
            .collect(Collectors.toSet());

    return csvSet;
  }

  public static boolean hasCommonEntry(Set<String> set1, Set<String> set2) {
    if (set1 == null || set2 == null || set1.isEmpty() || set2.isEmpty()) return false;

    // Iterate the smaller set for better performance
    Set<String> smaller = set1.size() <= set2.size() ? set1 : set2;
    Set<String> larger = set1.size() <= set2.size() ? set2 : set1;

    for (String value : smaller) {
      if (larger.contains(value)) {
        return true;
      }
    }
    return false;
  }
}
