package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Mindmap;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertNotNull;

public class XmindParserTest {
  @Test
  public void testObjectCreation() {
    MindmapParser xmindParser = new XmindParser();
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("Covid-19.xmind").getFile());
    assertNotNull(new Mindmap(xmindParser.parse(file.getAbsolutePath()), null, null));
  }
}
