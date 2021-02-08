package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Topic;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


public class TestHelper {
  protected static boolean topicsEquals(Topic first, Topic second) {
    return TestHelper.isSameObject(first, second)
            || (!TestHelper.singleNull(first, second)
            && StringUtils.equals(first.getId(), second.getId())
            && StringUtils.equals(first.getTitle(), second.getTitle())
            && StringUtils.equals(first.getNote(), second.getNote())
            && StringUtils.equals(first.getLink(), second.getLink())
            && TestHelper.isLabelsEqual(first, second)
            && TestHelper.isSubtopicsEqual(first, second));
  }

  private static boolean isSameObject(Topic first, Topic second) {
    return (first == null && second == null) || (first != null && first.equals(second));
  }

  private static boolean isLabelsEqual(Topic first, Topic second) {
    return !TestHelper.singleNull(first.getLabels(), second.getLabels())
            && !(first.getLabels() != null && second.getLabels() != null
            && !first.getLabels().equals(second.getLabels()));
  }

  private static boolean isSubtopicsEqual(Topic first, Topic second) {
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
    return true;
  }

  private static boolean singleNull(Object first, Object second) {
    return (first == null && second != null) || (first != null && second == null);
  }

  protected static Topic constructTopicFromJson(String fileName) {
    StringBuffer content = new StringBuffer();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(new File(fileName)));
      String line;
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
    return new Gson().fromJson(content.toString(), Topic.class);
  }
}
