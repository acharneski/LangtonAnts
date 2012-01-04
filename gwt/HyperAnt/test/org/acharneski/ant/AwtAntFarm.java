package org.acharneski.ant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.acharneski.ant.client.AntFarm;
import org.acharneski.ant.client.Ant.Point;

public class AwtAntFarm extends AntFarm implements Runnable
{
  public interface AwtAntFarmEvents
  {
    void onChange();
  }
  
  public AwtAntFarmEvents events = null;
  public final BufferedImage image;

  AwtAntFarm()
  {
    this(800, 600);
  }

  public AwtAntFarm(int w, int h)
  {
    super(w,h);
    image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
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
    image.getRaster().setPixel(p.x, p.y, color);
    if(null != events) events.onChange();
    return p;
  }

  public void run(int generations)
  {
    for(int i=0;i<generations;i++)
    {
      step();
    }
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

  public void write(File file) throws IOException
  {
    ImageIO.write(image, "jpeg", file);
  }
}