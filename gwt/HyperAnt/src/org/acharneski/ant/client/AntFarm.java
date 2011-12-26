package org.acharneski.ant.client;

import java.util.ArrayList;
import java.util.List;

import org.acharneski.ant.client.Ant.Point;

public class AntFarm
{

  public final int width;
  public final int height;
  public final byte data[];
  public final List<Ant> ants = new ArrayList<Ant>();

  public AntFarm()
  {
    this(800, 600);
  }

  public AntFarm(int width, int height)
  {
    this.width = width;
    this.height = height;
    data = new byte[width * height];
  }

  public byte get(Point p)
  {
    p = p.mod(width, height);
    return data[toIndex(p.x, p.y)];
  }

  public Point set(Point p, byte b)
  {
    p = p.mod(width, height);
    data[toIndex(p.x, p.y)] = b;
    return p;
  }

  private int toIndex(int x, int y)
  {
    return (x * height) + y;
  }

  protected void step()
  {
    for(Ant ant : ants)
    {
      ant.step();
    }
  }

  public void add(Ant ant)
  {
    ant.setFarm(this);
    ants.add(ant);
  }

}
