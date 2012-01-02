package org.acharneski.ant.client;

import java.util.Random;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HyperAnt implements EntryPoint
{
  private Canvas canvas;
  final String holderId = "ants";
  final Random random = new Random();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad()
  {
    RootPanel rootPanel = RootPanel.get(holderId);
    if (rootPanel == null)
    {
      Window.alert("ant element not found");
      return;
    }
    canvas = Canvas.createIfSupported();
    if (canvas == null)
    {
      rootPanel.add(new Label("HTML5 Canvas not supported"));
      return;
    }
    int width = Window.getClientWidth();
    int height = Window.getClientHeight();
    canvas.setWidth(width + "px");
    canvas.setHeight(height + "px");
    canvas.setCoordinateSpaceWidth(width);
    canvas.setCoordinateSpaceHeight(height);
    rootPanel.add(canvas);
    final AntFarm farm = createAntFarm(width, height);
    NodeList<Node> childNodes = rootPanel.getElement().getChildNodes();
    boolean isFirst = true;
    for(int i=0; i<childNodes.getLength(); i++)
    {
      Node item = childNodes.getItem(i);
      if("ANT".equals(item.getNodeName()))
      {
        for(int j=0; j<item.getChildCount(); j++)
        {
          Node item1 = item.getChild(j);
          if("#text".equals(item1.getNodeName()))
          {
            int x;
            int y;
            if (isFirst)
            {
              x = (int) (0.5 * width);
              y = (int) (0.5 * height);
              isFirst = false;
            }
            else
            {
              x = (int) (random.nextDouble() * width);
              y = (int) (random.nextDouble() * height);
            }
            farm.add(new RLCodeAnt(x, y, item1.getNodeValue()));
            item1.setNodeValue("");
          }
        }
      }
    }
    //print("", childNodes);
    new Timer()
    {
      int stepSize = 100;
      double targetTime = 10.;
      int stepsBetweenClear = 1000;
      int stepsUntilClear = stepsBetweenClear;
      @Override
      public void run()
      {
        if(0 >= stepsUntilClear--)
        {
          stepsUntilClear = stepsBetweenClear;
          farm.clear();
        }
        else
        {
          long start = System.currentTimeMillis();
          for(int i=0;i<stepSize;i++)
          {
            farm.step();
          }
          double time = System.currentTimeMillis() - start;
          stepSize *= targetTime / time;
        }
      }
    }.scheduleRepeating(10);
  }

  @SuppressWarnings("unused")
  private void print(String indent, NodeList<Node> childNodes)
  {
    for(int i=0; i<childNodes.getLength(); i++)
    {
      Node item = childNodes.getItem(i);
      System.out.println(indent + item.getNodeName() + " = " + item.getNodeValue());
      print(indent+" ", item.getChildNodes());
    }
  }

  private AntFarm createAntFarm(int width, int height)
  {
    final AntFarm farm = new GwtAntFarm(canvas.getContext2d(), width, height);
    //farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    return farm;
  }
}
