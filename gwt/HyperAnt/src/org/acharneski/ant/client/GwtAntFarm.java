package org.acharneski.ant.client;

import org.acharneski.ant.client.Ant.Point;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;

public class GwtAntFarm extends AntFarm
{

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

  private final Context2d context2d;
  public int stepsBetweenClear = 1000;

  public GwtAntFarm(Canvas canvas)
  {
    super(canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
    this.context2d = canvas.getContext2d();
  }

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

  int stepSize = 100;
  double targetTime = 10.;
  int stepsUntilClear = stepsBetweenClear;
  
  public Timer start()
  {
    Timer timer = new Timer()
    {
      @Override
      public void run()
      {
        if(0 < stepsBetweenClear && 0 >= stepsUntilClear--)
        {
          stepsUntilClear = stepsBetweenClear;
          GwtAntFarm.this.clear();
        }
        else
        {
          long start = System.currentTimeMillis();
          for(int i=0;i<stepSize;i++)
          {
            GwtAntFarm.this.step();
          }
          double time = System.currentTimeMillis() - start;
          stepSize *= targetTime / time;
        }
      }
    };
    timer.scheduleRepeating(10);
    return timer;
  }

}
