package model.labels;

import utils.Validator;

public enum Label {
  MENU_BAR("menu_bar", true),
  TEXT_FIELD("text_field", true),
  BUTTON("button", true),
  LABEL("label", true),
  CHECKED_CHECK_BOX("checked_check_box", false),
  UNCHECKED_CHECK_BOX("unchecked_check_box", false);

  String name;
  boolean hasText;

  Label(String name, boolean hasText) {
    this.name = name;
    this.hasText = hasText;
  }

  public String getName() {
    return name;
  }

  public boolean hasText() {
    return hasText;
  }

  public static Label fromString(String name) {
    if (Validator.isNullOrEmpty(name))
      return null;
    for (Label label : values()) {
      if (label.getName().equalsIgnoreCase(name))
        return label;
    }
    return null;
  }
}
