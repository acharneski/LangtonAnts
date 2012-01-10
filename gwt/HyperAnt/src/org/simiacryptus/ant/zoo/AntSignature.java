package org.simiacryptus.ant.zoo;

import java.util.Arrays;

public class AntSignature
{
  public final int histogram[];

  public AntSignature(int[] histogram)
  {
    super();
    this.histogram = histogram;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(histogram);
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AntSignature other = (AntSignature) obj;
    if (!Arrays.equals(histogram, other.histogram))
      return false;
    return true;
  }
}