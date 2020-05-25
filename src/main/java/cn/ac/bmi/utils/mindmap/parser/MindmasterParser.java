package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;

import java.io.InputStream;

public class MindmasterParser extends MindmapParser {
  @Override
  Sheet[] parse(InputStream mindmapInputStream) {
    if (mindmapInputStream == null) {
      return null;
    }

    return new Sheet[0];
  }
}
