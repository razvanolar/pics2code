package model.labels;

import utils.Validator;

public enum Label {
  MENU_BAR("menu_bar", true, "menu_bar", ""),
  TEXT_FIELD("text_field", true, "text_field", "EditText"),
  BUTTON("button", true, "button", "Button"),
  LABEL("label", true, "label", "TextView"),
  CHECKED_CHECK_BOX("checked_check_box", false, "check_box", ""),
  UNCHECKED_CHECK_BOX("unchecked_check_box", false, "check_box", "");

  // name used in xml files
  String name;
  // prefix used to compute an id when generating the code
  String idPrefix;
  // tag used for the component when generating the code
  String tag;
  boolean hasText;

  Label(String name, boolean hasText, String idPrefix, String tag) {
    this.name = name;
    this.hasText = hasText;
    this.idPrefix = idPrefix;
    this.tag = tag;
  }

  public String getName() {
    return name;
  }

  public boolean hasText() {
    return hasText;
  }

  public String getIdPrefix() {
    return idPrefix;
  }

  public String getTag() {
    return tag;
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
