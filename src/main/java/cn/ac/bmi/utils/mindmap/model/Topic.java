package cn.ac.bmi.utils.mindmap.model;

import lombok.Setter;

import java.util.Set;

public class Topic {
  private String id;
  private String title;
  @Setter private Topic[] topics;
  private Set<String> labels;
  private String note;
  private String link;

  private Sheet belongTo;

  public Topic(String id, String title, Topic[] topics, Set<String> labels, String note, String link) {
    this.id = id;
    this.title = title;
    this.topics = topics;
    this.labels = labels;
    this.note = note;
    this.link = link;
  }

  public Topic init(Sheet sheet) {
    this.belongTo = sheet;

    this.title = this.title == null ? "" : this.title.trim();
    if (topics != null) {
      for (Topic child: topics) {
        child.init(sheet);
      }
    }

    return this;
  }

  public Topic(String title, Sheet belongTo) {
    this.title = title;
    this.belongTo = belongTo;
  }
}
