package com.gastonfournier.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
  public static List<String> readFileAsLines(String filename) {
    List<String> lines = new ArrayList<>();
    try {
      ClassLoader classLoader = Reader.class.getClassLoader();
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filename)));
      String line = reader.readLine();

      while (line != null) {
        lines.add(line);
        line = reader.readLine();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return lines;
  }

  public static String readFileAsString(String filename) {
    StringBuilder lines = new StringBuilder();
    try {
      ClassLoader classLoader = Reader.class.getClassLoader();
      BufferedReader reader =
              new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filename)));
      String line = reader.readLine();

      while (line != null) {
        lines.append(line).append("\n");
        line = reader.readLine();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return lines.toString();
  }
}
