package cn.ac.bmi.utils.mindmap.parser;

import com.google.gson.Gson;

import cn.ac.bmi.utils.mindmap.model.Topic;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelper {
  public static boolean topicsEquals(Topic first, Topic second) {
    if (first.equals(second)) {
      return true;
    }
    if ((first == null || second == null) || (first.getClass() != second.getClass())) {
      return false;
    }
    if (!StringUtils.equals(first.getId(), second.getId())
            || !StringUtils.equals(first.getTitle(), second.getTitle())
            || !StringUtils.equals(first.getNote(), second.getNote())
            || !StringUtils.equals(first.getLink(), second.getLink())
    ) {
      return false;
    }

    /* test if child topics of the two topics are equal recursively */
    if (TestHelper.singleNull(first.getTopics(), second.getTopics())) {
      return false;
    }
    if (first.getTopics() != null && second.getTopics() != null) {
      List<Topic> firstChildTopics = new ArrayList<>(Arrays.asList(first.getTopics()));
      List<Topic> secondChildTopics = new ArrayList<>(Arrays.asList(second.getTopics()));
      int childrenCount = firstChildTopics.size();
      if (childrenCount != secondChildTopics.size()) {
        return false;
      }
      for (int i = 0; i < childrenCount; i++) {
        if (!TestHelper.topicsEquals(firstChildTopics.get(i), secondChildTopics.get(i))) {
          return false;
        }
      }
    }

    /* test if labels of the two topics are equal, NOTICE: labels may be null, but not be empty set*/
    if (TestHelper.singleNull(first.getLabels(), second.getLabels())) {
      return false;
    }
    /* test if the two sets(String) are equal */
    if (first.getLabels() != null && second.getLabels() != null
            && !first.getLabels().equals(second.getLabels())) {
      return false;
    }

    return true;
  }

  private static boolean singleNull(Object first, Object second) {
    return (first == null && second != null) || (first != null && second == null);
  }

  public static Topic constructTopicFromJson(String jsonFilePath) {
    String content = TestHelper.getFileContent(jsonFilePath);
    Gson gson = new Gson();
    Topic fromJson = gson.fromJson(content, Topic.class);
    return fromJson;
  }

  private static String getFileContent(String fileName) {
    StringBuffer content = new StringBuffer();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(new File(fileName)));
      String line = null;
      while ((line = reader.readLine()) != null) {
        content.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return content.toString();
  }
}