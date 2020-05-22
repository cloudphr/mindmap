package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import cn.ac.bmi.utils.mindmap.model.Topic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;

import static org.testng.Assert.*;

public class XmindParserTest {
  private ClassLoader classLoader = getClass().getClassLoader();
  private MindmapParser parser;

  @BeforeMethod
  public void setUp() throws Exception {
    this.parser = new XmindParser();
  }

  @Test
  public void testParseNotExistFile() {
    Sheet[] sheets = this.parser.parse("SOME_FILE_NOT_EXISTS");
    assertNull(sheets);
  }

  @Test
  public void testParseNotXmindFile() {
    File file = new File(this.classLoader.getResource("notxmind.xmind").getFile());
    Sheet[] sheets = this.parser.parse(file.getAbsolutePath());
    assertNull(sheets);
  }

  @Test
  public void testParseClosedInputStream() {
    File file = new File(this.classLoader.getResource("blank.xmind").getFile());
    try {
      InputStream inputStream = new FileInputStream(file);
      try {
        inputStream.close();
      } catch (IOException ioe) {
        assert false;
      }
      Sheet[] sheets = this.parser.parse(inputStream);
      assertNull(sheets);
    } catch (FileNotFoundException e) {
      assert false;
    }
  }

  @Test
  public void testParseNullFileInputStream() {
    InputStream inputStream = null;
    Sheet[] sheets = this.parser.parse(inputStream);
    assertNull(sheets);
  }

  @Test
  public void testParseBlank() {
    File file = new File(this.classLoader.getResource("blank.xmind").getFile());
    Sheet[] sheets = this.parser.parse(file.getAbsolutePath());
    assertNotNull(sheets);
    assertEquals(sheets.length, 1);
    assertEquals(sheets[0].getTitle(), "Sheet 1");
    assertEquals(sheets[0].getStructure(), "org.xmind.ui.map.unbalanced");
    assertNotNull(sheets[0].getTopic());

    Topic root = sheets[0].getTopic();
    assertNotNull(root.getTitle(), "blank");
    assertNull(root.getTopics());
    // assertEquals(root.getBelongTo(), sheets[0]);
    assertEquals(root.getId(), "3ls2e0296d00keih93noh7bh0b");
    assertNull(root.getLabels());
    assertNull(root.getLink());
    assertNull(root.getNote());
  }
}
