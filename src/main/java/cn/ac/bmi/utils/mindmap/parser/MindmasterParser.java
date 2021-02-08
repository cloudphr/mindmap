package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import java.io.InputStream;

public class MindmasterParser implements MindmapParser {
  @Override
  public Sheet[] parse(InputStream mindmapInputStream) {
    if (mindmapInputStream == null) {
      return new Sheet[0];
    }

    return new Sheet[0];
  }
}
