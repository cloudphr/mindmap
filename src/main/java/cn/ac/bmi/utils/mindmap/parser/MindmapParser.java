package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class MindmapParser {
  public Sheet[] parse(String mindmapPath) {
    InputStream mindmaapInputStream = null;
    try {
      mindmapPath = mindmapPath == null ? null : mindmapPath.trim();
      mindmaapInputStream = new FileInputStream(mindmapPath);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return parse(mindmaapInputStream);
  }

  abstract Sheet[] parse(InputStream mindmapInputStream);
}
