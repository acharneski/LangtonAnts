package org.simiacryptus.ant.awt;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.simiacryptus.ant.common.AntFarm;
import org.simiacryptus.ant.common.Ant.Point;

public class AwtAntFarm extends AntFarm implements Runnable
{
  public interface AwtAntFarmEvents
  {
    void onChange();
  }
  
  public AwtAntFarmEvents events = null;
  public final BufferedImage image;
  
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
  public final int histogram[] = new int[colors.length];

  public AwtAntFarm()
  {
    this(800, 600);
  }

  public AwtAntFarm(int w, int h)
  {
    super(w,h);
    image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    histogram[0] = width * height;
  }

  public double fillRatio()
  {
    return 1. - ((double)histogram[0] / (width * height));
  }

  @Override
  public Point set(Point p, byte b)
  {
    byte oldValue = super.get(p);
    histogram[oldValue]--;
    histogram[b]++;
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
    ImageIO.write(image, "png", file);
  }

  public String write() throws IOException
  {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    ImageIO.write(image, "png", stream);
    return "data:image/png;base64," + javax.xml.bind.DatatypeConverter.printBase64Binary(stream.toByteArray());
  }
}