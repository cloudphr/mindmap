package cn.ac.bmi.utils.mindmap;

import lombok.Getter;

public class Mindmap {
  @Getter
  private Sheet[] sheets;

  public Mindmap(Sheet[] sheets) {
    this.sheets = sheets;
    this.init();
  }

  private void init() {
    if (this.sheets != null) {
      for (Sheet sheet : this.sheets) {
        sheet.init();
      }
    }
  }
}
