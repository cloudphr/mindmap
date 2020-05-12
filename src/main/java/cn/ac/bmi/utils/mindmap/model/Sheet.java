package cn.ac.bmi.utils.mindmap.model;

public class Sheet {
  private String title;
  private Topic topic;
  private String structure;

  public Sheet(String title, Topic topic, String structure) {
    this.title = title;
    this.topic = topic;
    this.structure = structure;
  }

  public Sheet init() {
    title = title == null ? "" : title.trim();
    if (topic != null) {
      topic.init(this);
    }
    return this;
  }
}
