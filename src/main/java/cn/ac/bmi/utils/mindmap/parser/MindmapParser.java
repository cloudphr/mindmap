package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class MindmapParser {
  public Sheet[] parse(String mindmapPath) {
    InputStream mindmapInputStream = null;
    try {
      mindmapPath = mindmapPath == null ? null : mindmapPath.trim();
      mindmapInputStream = new FileInputStream(mindmapPath);
      return parse(mindmapInputStream);
    } catch (IOException ioe) {
      return null;
    }
  }

  abstract Sheet[] parse(InputStream mindmapInputStream);
}
