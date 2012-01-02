package org.acharneski.ant;

import org.acharneski.ant.client.AntFarm;
import org.acharneski.ant.client.Ant.Point;

public class AwtAntFarm extends AntFarm implements Runnable
{
  private final AntFrame frame;

  AwtAntFarm(AntFrame frame)
  {
    this.frame = frame;
  }

  int colors[][] = {
      { 0, 0, 0 },
      { 255, 255, 255 },
      { 0, 255, 255 },
      { 255, 0, 255 },
      { 255, 255, 0 },
      { 255, 0, 0 },
      { 0, 255, 0 },
      { 0, 0, 255 }
  };

  @Override
  public Point set(Point p, byte b)
  {
    p = super.set(p, b);
    int[] color = colors[b];
    frame.image.getRaster().setPixel(p.x, p.y, color);
    frame.repaint();
    return p;
  }

  @Override
  public void run()
  {
    while (this.continueLoop)
    {
      step();
    }
  }

  private boolean continueLoop = true;

  public void stop()
  {
    this.continueLoop = false;
  }
}