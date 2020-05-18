package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class MindmapParser {
  public static Logger LOG = LogManager.getLogger(MindmapParser.class);
  public Sheet[] parse(String mindmapPath) {
    InputStream mindmapInputStream = null;
    try {
      mindmapPath = mindmapPath == null ? null : mindmapPath.trim();
      mindmapInputStream = new FileInputStream(mindmapPath);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return parse(mindmapInputStream);
  }

  abstract Sheet[] parse(InputStream mindmapInputStream);
}
