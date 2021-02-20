package cn.ac.bmi.mindmap.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import cn.ac.bmi.mindmap.model.Sheet;
import cn.ac.bmi.mindmap.model.Topic;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XmindParserTest {
  private ClassLoader classLoader;
  private MindmapParser parser;

  @BeforeMethod
  public void setUp() throws Exception {
    this.parser = new XmindParser();
    this.classLoader = this.getClass().getClassLoader();
    assertNotNull(this.parser);
    assertNotNull(this.classLoader);
  }

  @Test
  public void testFileNotFoundException() {
    String pathNotExist = "notExist.xmind";
    Sheet[] sheets = this.parser.parse(pathNotExist);
    assertNotNull(sheets);
    assertEquals(sheets.length, 0);
  }

  @Test
  public void testNullStringAsPath() {
    String nullStringAsPath = null;
    Sheet[] sheets = this.parser.parse(nullStringAsPath);
    assertNotNull(sheets);
    assertEquals(sheets.length, 0);
  }

  @Test
  public void testBadXmindContent() {
    File file = new File(this.classLoader.getResource("bad.content.xmind").getFile());
    Sheet[] sheets = this.parser.parse(file.getAbsolutePath());
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
    assertEquals(root.getTitle(), "blank");
    assertNull(root.getTopics());
    // assertEquals(root.getBelongTo(), sheets[0]);
    assertEquals(root.getId(), "3ls2e0296d00keih93noh7bh0b");
    assertNull(root.getLabels());
    assertNull(root.getLink());
    assertNull(root.getNote());
  }

  @Test
  public void testParseSimpleXmindFileUsingTestHelper() {
    File file = new File(this.classLoader.getResource("simple-1.xmind").getFile());
    Sheet[] sheets = this.parser.parse(file.getAbsolutePath());
    /* Test 'sheets' is same as expected */
    assertNotNull(sheets);
    assertEquals(sheets.length, 1);
    assertEquals(sheets[0].getTitle(), "Only&*Sheet");
    assertEquals(sheets[0].getStructure(), "org.xmind.ui.logic.right");
    assertNotNull(sheets[0].getTopic());
    Topic firstTopic = sheets[0].getTopic();
    File jsonFile = new File(this.classLoader.getResource("simple-1.json").getFile());
    Topic secondTopic = TestHelper.constructTopicFromJson(jsonFile.getAbsolutePath());
    assertEquals(TestHelper.topicsEquals(firstTopic, secondTopic), true);
  }
}
