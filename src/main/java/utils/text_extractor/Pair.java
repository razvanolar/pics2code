package utils.text_extractor;

public class Pair<A, B> {

  private A firstValue;
  private B secondValue;

  public Pair(A firstValue, B secondValue) {
    this.firstValue = firstValue;
    this.secondValue = secondValue;
  }

  public A getFirstValue() {
    return firstValue;
  }

  public B getSecondValue() {
    return secondValue;
  }
}
