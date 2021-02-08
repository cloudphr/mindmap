package cn.ac.bmi.utils.mindmap.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import java.io.InputStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MindmapParserTest {
  private MindmapParser parser;

  @BeforeMethod
  public void setUp() throws Exception {
    this.parser = new MindmapParser() {
      @Override
      public Sheet[] parse(InputStream mindmapInputStream) {
        return new Sheet[0];
      }
    };
  }

  @Test
  public void testParseNotExistFile() {
    Sheet[] sheets = this.parser.parse("SOME_FILE_NOT_EXISTS");
    assertNotNull(sheets);
    assertEquals(sheets.length, 0);
  }
}