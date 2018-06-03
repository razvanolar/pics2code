package model;

import model.labels.Label;

import java.util.Objects;

public class Form {

  private Label label;
  private Box box;
  private Bias bias;

  public Form() {}

  public Form(Label label, Box box, Bias bias) {
    this.label = label;
    this.box = box;
    this.bias = bias;
  }

  public Label getLabel() {
    return label;
  }

  public Box getBox() {
    return box;
  }

  public Bias getBias() {
    return bias;
  }

  public void setLabel(Label label) {
    this.label = label;
  }

  public void setBox(Box box) {
    this.box = box;
  }

  public void setBias(Bias bias) {
    this.bias = bias;
  }

  @Override
  public String toString() {
    return label.name();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Form form = (Form) o;
    return label == form.label &&
        Objects.equals(box, form.box);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, box);
  }
}
