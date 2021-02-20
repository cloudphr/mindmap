package cn.ac.bmi.mindmap.parser;

import cn.ac.bmi.mindmap.model.Sheet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface MindmapParser {
  default Sheet[] parse(String mindmapPath) {
    if (mindmapPath == null) {
      return new Sheet[0];
    }

    try {
      mindmapPath = mindmapPath.trim();
      InputStream mindmapInputStream = new FileInputStream(mindmapPath);
      return parse(mindmapInputStream);
    } catch (IOException ioe) {
      return new Sheet[0];
    }
  }

  Sheet[] parse(InputStream mindmapInputStream);
}
