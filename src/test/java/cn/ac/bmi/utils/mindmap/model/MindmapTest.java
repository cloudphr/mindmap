package cn.ac.bmi.utils.mindmap.model;

import cn.ac.bmi.utils.mindmap.parser.MindmapParser;
import cn.ac.bmi.utils.mindmap.parser.XmindParser;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class MindmapTest {
  @Test
  public void testObjectCreation() {
    assertNotNull(new Mindmap(new Sheet[0], null,null));
  }
}
