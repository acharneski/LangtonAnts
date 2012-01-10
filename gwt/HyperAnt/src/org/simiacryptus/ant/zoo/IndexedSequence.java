package org.simiacryptus.ant.zoo;

public class IndexedSequence
{

  private int value;
  private int max;

  public IndexedSequence(int i, int m)
  {
    this.value = i;
    this.max = m;
  }

  public boolean hasNext(int base)
  {
    return base <= max;
  }

  public int getNext(int base)
  {
    int next = value % base;
    value = value / base;
    max = max / base;
    return next;
  }

}
