package cn.ac.bmi.mindmap.parser;

import cn.ac.bmi.mindmap.model.Sheet;
import java.io.InputStream;

public class MindmasterParser implements MindmapParser {
  @Override
  public Sheet[] parse(InputStream mindmapInputStream) {
    return new Sheet[0];
  }
}
