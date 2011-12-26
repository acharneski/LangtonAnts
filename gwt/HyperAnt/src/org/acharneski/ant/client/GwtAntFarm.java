package org.acharneski.ant.client;

import org.acharneski.ant.client.Ant.Point;

import com.google.gwt.canvas.dom.client.Context2d;

public class GwtAntFarm extends AntFarm
{

  private final Context2d context2d;

  public GwtAntFarm(Context2d context2d2)
  {
    this.context2d = context2d2;
  }

  @Override
  public Point set(Point p, byte b)
  {
    p = super.set(p, b);
    context2d.fillRect(p.x, p.y, 1, 1);
    return p;
  }
  
  

}
