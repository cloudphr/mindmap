package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;

import static org.testng.Assert.*;

public class MindmapParserTest {
  private MindmapParser parser;

  @BeforeMethod
  public void setUp() throws Exception {
    this.parser = new MindmapParser() {
      @Override
      Sheet[] parse(InputStream mindmapInputStream) {
        return new Sheet[0];
      }
    };
  }

  @Test
  public void testParseNotExistFile() {
    Sheet[] sheets = this.parser.parse("SOME_FILE_NOT_EXISTS");
    assertNull(sheets);
  }
}