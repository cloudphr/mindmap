package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import cn.ac.bmi.utils.mindmap.model.Topic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashSet;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

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
    assertNull(sheets);
  }

  @Test
  public void testNullStringAsPath() {
    String nullStringAsPath = null;
    Sheet[] sheets = this.parser.parse(nullStringAsPath);
    assertNull(sheets);
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
  public void testParseSimpleXmindFile() {
    File file = new File(this.classLoader.getResource("simple-1.xmind").getFile());
    Sheet[] sheets = this.parser.parse(file.getAbsolutePath());
    /* Test 'sheets' is same as expected */
    assertNotNull(sheets);
    assertEquals(sheets.length, 1);
    assertEquals(sheets[0].getTitle(), "Only&*Sheet");
    assertEquals(sheets[0].getStructure(), "org.xmind.ui.logic.right");
    assertNotNull(sheets[0].getTopic());

    /* Test 'root topic of sheet[0](the only sheet of simple-1 example)' is same as expected */
    Topic root = sheets[0].getTopic();
    assertEquals(root.getTitle(), "门急诊信息/Outpatient");
    assertEquals(root.getLabels(), new HashSet<String>() {
      {
        add("Hello");
      }
    });
    assertEquals(root.getNote(), "Notes\napi:");
    Topic[] topics = root.getTopics();
    assertNotNull(topics);
    assertEquals(topics.length, 7);

    /* first child topic */
    Topic firstTopic = topics[0];
    assertEquals(firstTopic.getId(), "63b6e0n2sf2292n137oaip20l2");
    assertEquals(firstTopic.getTitle(), "数组含数组/Array of Array: aoas");
    assertEquals(firstTopic.getLabels(), new HashSet<String>() {
      {
        add("+_)(*&^%$#@!~?><:\".");
        add("CLUSTER.ArrayOfArrays");
        add("repeat");
      }
    });

    /* 2nd child topic */
    Topic secondTopic = topics[1];
    assertEquals(secondTopic.getId(), "3bqtbtvbauq3gmcgev1bkke19t");
    assertEquals(secondTopic.getTitle(), "属性/Property: prop");
    assertEquals(secondTopic.getLabels(), new HashSet<String>() {
      {
        add("TEXT，全角逗号");
        add("第2个label");
      }
    });

    /* 3rd child topic */
    Topic thirdTopic = topics[2];
    assertEquals(thirdTopic.getId(), "5t3en1rp0sb40vb3hpk8e6uudc");
    assertEquals(thirdTopic.getTitle(), "编码文本/Encoded Text: dict");
    assertEquals(thirdTopic.getLabels(), new HashSet<String>() {
      {
        add("DV_TEXT");
      }
    });

    /* 4th child topic */
    Topic fourthTopic = topics[3];
    assertEquals(fourthTopic.getId(), "7p454iln6d0jubl0743ncksjds");
    assertEquals(fourthTopic.getTitle(), "术语表/Terminology: diagnosis");
    assertEquals(fourthTopic.getLabels(), new HashSet<String>() {
      {
        add("DV_TEXT");
      }
    });

    /* 5th child topic */
    Topic fifthTopic = topics[4];
    assertEquals(fifthTopic.getId(), "3ligffbdjhlgoqajsdgmb6ls3n");
    assertEquals(fifthTopic.getTitle(), "内部结构体/Inside Structure: object");
    assertEquals(fifthTopic.getLabels(), new HashSet<String>() {
      {
        add("repeat");
        add("cn.ac.bmi::openEHR-EHR-CLUSTER.person.v0");
      }
    });

    /* 6th child topic */
    Topic sixthTopic = topics[5];
    assertEquals(sixthTopic.getId(), "5irdcahq8862j5qm6iat61jh8l");
    assertEquals(sixthTopic.getTitle(), "内部结构体1/Inside Structure1: default");
    assertEquals(sixthTopic.getLabels(), new HashSet<String>() {
      {
        add("NOLABLE: default inside cluster");
      }
    });

    /* 7th child topic */
    Topic seventhTopic = topics[6];
    assertEquals(seventhTopic.getId(), "5e9fv4c687t08vcrsp03to120g");
    assertEquals(seventhTopic.getTitle(), "引用结构体/Outside Structure: ref");
    assertEquals(seventhTopic.getLink(), "xmind:#3ligffbdjhlgoqajsdgmb6ls3n");
    assertEquals(seventhTopic.getLabels(), new HashSet<String>() {
      {
        add("CLUSTER.OutsideStructure");
      }
    });

  }
}
