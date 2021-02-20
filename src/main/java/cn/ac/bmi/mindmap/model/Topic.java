package cn.ac.bmi.mindmap.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Topic {
  private String id;
  private String title;
  @Setter private Topic[] topics;
  private Set<String> labels;
  private String note;
  private String link;
  @Setter private Sheet belongTo;

  public Topic(String id, String title, Topic[] topics, Set<String> labels, String note, String link) {
    this.id = id;
    this.title = title;
    this.topics = topics;
    this.labels = labels;
    this.note = note;
    this.link = link;
  }

  public Topic(String title, Sheet belongTo) {
    this.title = title;
    this.belongTo = belongTo;
  }

  public Topic(String title, String type, Sheet belongTo) {
    this(title, belongTo);
    this.labels = new HashSet<>();
    this.labels.add(type);
  }

  public Topic init(Sheet belongTo) {
    this.belongTo = belongTo;
    this.title = this.title == null ? "" : this.title.trim();

    Set<String> newLabels = new HashSet<>();
    if (labels != null) {
      for (String label : labels) {
        label = label.trim();
        newLabels.add(label);
      }
    }
    this.labels = newLabels;

    if (topics != null) {
      for (Topic child : topics) {
        child.init(belongTo);
        this.belongTo.getTopics().put(child.id, child);
      }
    }

    return this;
  }

  @Override
  public String toString() {
    if (this.belongTo == null) {
      return "Topic[" + this.title + "@null]";
    } else {
      return "Topic[" + this.title + "@" + this.belongTo.getTitle() + "]";
    }
  }
}
