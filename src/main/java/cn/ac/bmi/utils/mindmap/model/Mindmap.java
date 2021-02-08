package cn.ac.bmi.utils.mindmap.model;

import java.util.function.Consumer;
import lombok.Getter;

public class Mindmap {
  @Getter private Sheet[] sheets;
  Consumer<Sheet> sheetHook;
  Consumer<Topic> topicHook;

  public Mindmap(Sheet[] sheets, Consumer<Sheet> sheetHook, Consumer<Topic> topicHook) {
    this.sheets = sheets;
    this.sheetHook = sheetHook;
    this.topicHook = topicHook;
    this.init();
  }

  private void init() {
    if (this.sheets != null) {
      for (Sheet sheet : this.sheets) {
        sheet.init(sheetHook, topicHook);
      }
    }
  }
}
