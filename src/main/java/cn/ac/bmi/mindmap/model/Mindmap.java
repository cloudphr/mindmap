package cn.ac.bmi.mindmap.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class Mindmap {
  @Getter private Sheet[] sheets;
  private Map<String, Topic> topics = new HashMap<>();

  public Mindmap(Sheet[] sheets) {
    this.sheets = sheets;
    this.init();
  }

  public Topic getTopic(String id) {
    return topics.get(id);
  }

  private void init() {
    if (this.sheets != null) {
      for (Sheet sheet : this.sheets) {
        if (sheet == null) {
          continue;
        }
        sheet.init();
        sheet.setBelongTo(this);
        this.topics.putAll(sheet.getTopics());
      }
    }
  }
}
