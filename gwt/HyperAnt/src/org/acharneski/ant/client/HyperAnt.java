package org.acharneski.ant.client;

import java.util.Random;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HyperAnt implements EntryPoint
{
  private Canvas canvas;
  final String holderId = "ants";
  final int width = 800;
  final int height = 600;
  final Random random = new Random();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad()
  {

    canvas = Canvas.createIfSupported();
    if (canvas == null)
    {
      RootPanel.get(holderId).add(new Label("Canvas not supported"));
      return;
    }
    canvas.setWidth(width + "px");
    canvas.setHeight(height + "px");
    canvas.setCoordinateSpaceWidth(width);
    canvas.setCoordinateSpaceHeight(height);
    RootPanel.get(holderId).add(canvas);
    final AntFarm farm = createAntFarm();
    new Timer()
    {
      int stepSize = 100;
      double targetTime = 10.;
      @Override
      public void run()
      {
        long start = System.currentTimeMillis();
        for(int i=0;i<stepSize;i++)
        {
          farm.step();
        }
        double time = System.currentTimeMillis() - start;
        stepSize *= targetTime / time;
      }
    }.scheduleRepeating(10);
  }

  private AntFarm createAntFarm()
  {
    final AntFarm farm = new GwtAntFarm(canvas.getContext2d());
    farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    return farm;
  }
}
