package org.simiacryptus.ant.gwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.simiacryptus.ant.common.Ant;
import org.simiacryptus.ant.common.Turnite;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HyperAnt implements EntryPoint
{
  final String holderId = "ants";
  final Random random = new Random();
  private int height = 300;
  private int width = 400;
  private Timer timer;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad()
  {
    AntLib.exportStaticMethod();
    RootPanel rootPanel = RootPanel.get(holderId);
    if(null != rootPanel)
    {
      Canvas canvas = initCanvas(rootPanel);
      List<Ant> ants = getAntElements(rootPanel);
      if(null == ants || 0 != ants.size())
      {
        final GwtAntFarm farm = new GwtAntFarm(canvas);
        for(Ant ant : ants)
        {
          farm.add(ant);
        }
        farm.stepsBetweenClear = 0;
        timer = farm.start();
        canvas.addClickHandler(new ClickHandler()
        {
          @Override
          public void onClick(ClickEvent event)
          {
            if(null != timer)
            {
              timer.cancel();
              timer = null;
            }
            else
            {
              timer = farm.start();
            }
          }
        });
      }
    }
  }

  private List<Ant> getAntElements(RootPanel rootPanel)
  {
    if(null == rootPanel) return null;
    ArrayList<Ant> list = new ArrayList<Ant>();
    Element element = rootPanel.getElement();
    NodeList<Node> childNodes = element.getChildNodes();
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
            list.add(new Turnite(x, y, item1.getNodeValue()));
            item1.setNodeValue("");
          }
        }
      }
    }
    return list;
  }

  private Canvas initCanvas(RootPanel rootPanel)
  {
    Canvas canvas;
    if (rootPanel == null)
    {
      return null;
    }
    canvas = Canvas.createIfSupported();
    if (canvas == null)
    {
      rootPanel.add(new Label("HTML5 Canvas not supported"));
      return null;
    }
    this.width = Window.getClientWidth();
    this.height = Window.getClientHeight();
    canvas.setWidth(width + "px");
    canvas.setHeight(height + "px");
    canvas.setCoordinateSpaceWidth(width);
    canvas.setCoordinateSpaceHeight(height);
    rootPanel.add(canvas);
    return canvas;
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

  static GwtAntFarm createAntFarm(Canvas canvas)
  {
    return new GwtAntFarm(canvas);
  }
}
