package model;

import java.util.Objects;

public class Box {

  private float xmin;
  private float ymin;
  private float xmax;
  private float ymax;

  public Box(float xmin, float ymin, float xmax, float ymax) {
    this.xmin = xmin;
    this.ymin = ymin;
    this.xmax = xmax;
    this.ymax = ymax;
  }

  public float getXmin() {
    return xmin;
  }

  public float getYmin() {
    return ymin;
  }

  public float getXmax() {
    return xmax;
  }

  public float getYmax() {
    return ymax;
  }

  public float getWidth() {
    return xmax - xmin;
  }

  public float getHeight() {
    return ymax - ymin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Box box = (Box) o;
    return Float.compare(box.xmin, xmin) == 0 &&
        Float.compare(box.ymin, ymin) == 0 &&
        Float.compare(box.xmax, xmax) == 0 &&
        Float.compare(box.ymax, ymax) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(xmin, ymin, xmax, ymax);
  }
}
