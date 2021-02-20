package cn.ac.bmi.mindmap.model;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class TopicTest {

  @Test
  public void topicCouldHaveNoBelongTo1() {
    Topic topic = new Topic("For Test", null);
    assertNotNull(topic);
    assertNull(topic.getBelongTo());
  }

  @Test
  public void topicCouldHaveNoBelongTo2() {
    Topic topic = new Topic("For Test",  "real", null);
    assertNotNull(topic);
    assertNull(topic.getBelongTo());
  }

  @Test
  public void topicToString() {
    Topic topic = new Topic("For Test",  "real", null);
    assertNotNull(topic);
    assertTrue(topic.toString().contains("For Test"));
  }
}