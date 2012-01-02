package org.acharneski.ant.client;

import org.acharneski.ant.client.Ant.Point;

import com.google.gwt.canvas.dom.client.Context2d;

public class GwtAntFarm extends AntFarm
{

  private final Context2d context2d;

  public GwtAntFarm(Context2d context2d2, int width, int height)
  {
    super(width, height);
    this.context2d = context2d2;
  }

  String colors[] = {
      "white",
      "black",
      "red",
      "green",
      "blue",
      "violet",
      "orange",
      "yellow"
  };

  @Override
  public Point set(Point p, byte b)
  {
    p = super.set(p, b);
    context2d.setFillStyle(colors[b]);
    context2d.fillRect(p.x, p.y, 1, 1);
    return p;
  }

  @Override
  public void clear()
  {
    context2d.setFillStyle(colors[0]);
    context2d.fillRect(0, 0, width, height);
    super.clear();
  }
  
  

}
