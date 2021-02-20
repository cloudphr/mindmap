package cn.ac.bmi.mindmap.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import cn.ac.bmi.mindmap.model.Sheet;
import java.io.InputStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MindmasterParserTest {
  private ClassLoader classLoader;
  private MindmapParser parser;

  @BeforeMethod
  public void setUp() throws Exception {
    this.parser = new MindmasterParser();
    this.classLoader = this.getClass().getClassLoader();
    assertNotNull(this.parser);
    assertNotNull(this.classLoader);
  }

  @Test
  public void testParse() {
    InputStream inputStream = null;
    Sheet[] sheets = this.parser.parse(inputStream);
    assertNotNull(sheets);
    assertEquals(sheets.length, 0);
  }
}