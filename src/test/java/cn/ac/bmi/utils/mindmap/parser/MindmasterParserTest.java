package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;

import static org.testng.Assert.*;

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
    assertNull(sheets);
  }
}