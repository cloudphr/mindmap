package cn.ac.bmi.utils.mindmap.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class MindmapTest {
  @Test
  public void testObjectCreation() {
    Mindmap mindmap = new Mindmap(new Sheet[0], null,null);
    assertNotNull(mindmap);
    Sheet[] sheets = mindmap.getSheets();
    assertNotNull(sheets);
    assertEquals(sheets.length, 0);
  }
}
