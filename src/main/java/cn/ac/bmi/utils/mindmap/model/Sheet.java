package cn.ac.bmi.utils.mindmap.model;

import lombok.Getter;

import java.util.function.Consumer;

public class Sheet {
  @Getter private String title;
  @Getter private Topic topic;
  @Getter private String structure;

  public Sheet(String title, Topic topic, String structure) {
    this.title = title;
    this.topic = topic;
    this.structure = structure;
  }

  public Sheet init(Consumer<Sheet> sheetHook, Consumer<Topic> topicHook) {
    title = title == null ? "" : title.trim();
    if (sheetHook != null) {
      sheetHook.accept(this);
    }

    if (topic != null) {
      topic.init(this, topicHook);
    }
    return this;
  }
}
