package model;

public class FormWithText {

  private Form form;
  private String text;

  public FormWithText(Form form, String text) {
    this.form = form;
    this.text = text;
  }

  public Form getForm() {
    return form;
  }

  public String getText() {
    return text == null ? form.getLabel().getTag() : text;
  }
}
