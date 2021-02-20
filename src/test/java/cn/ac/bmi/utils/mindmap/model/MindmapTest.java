package cn.ac.bmi.utils.mindmap.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class MindmapTest {
  @Test
  public void testObjectCreation() {
    Mindmap mindmap = new Mindmap(new Sheet[0]);
    assertNotNull(mindmap);
    Sheet[] sheets = mindmap.getSheets();
    assertNotNull(sheets);
    assertEquals(sheets.length, 0);
  }
}
