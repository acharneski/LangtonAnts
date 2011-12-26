package org.acharneski.ant.client;


public class Ant
{

  private Direction orientation;
  private AntFarm farm;
  private Point point;
  
  public static class Point
  {
    public final int x;
    public final int y;
    public Point(int x, int y)
    {
      super();
      this.x = x;
      this.y = y;
    }
    public Point add(Direction d){
      return new Point(x + d.dx, y + d.dy);
    }
    public Point mod(int width, int height)
    {
      int nx = x % width;
      int ny = y % height;
      while(nx < 0) nx += width;
      while(ny < 0) ny += height;
      return new Point(nx, ny);
    }
  }
  
  public enum Direction
  {
    Up(0,1),
    Down(0,-1),
    Left(-1,0),
    Right(1,0);
    public final int dx;
    public final int dy;
    
    public static Direction find(int dx, int dy)
    {
      for(Direction d : Direction.values())
      {
        if(d.dx == dx && d.dy == dy) return d;
      }
      return null;
    }

    private Direction(int dx, int dy)
    {
      this.dx = dx;
      this.dy = dy;
    }

    public Direction clockwise()
    {
      return find(dy, -dx);
    }
    
    public Direction counterClockwise()
    {
      return find(-dy, dx);
    }
  }

  public Ant(int x, int y)
  {
    this.setPoint(new Point(x,y));
    this.setOrientation(Direction.Down);
  }

  public void step()
  {
    if(((byte)0) == getFarm().get(point))
    {
      orientation = orientation.clockwise();
      getFarm().set(point, (byte) 1);
    }
    else
    {
      orientation = orientation.counterClockwise();
      getFarm().set(point, (byte) 0);
    }
    point = point.add(orientation);
  }

  public Direction getOrientation()
  {
    return orientation;
  }

  public void setOrientation(Direction orientation)
  {
    this.orientation = orientation;
  }

  public Point getPoint()
  {
    return point;
  }

  public void setPoint(Point point)
  {
    this.point = point;
  }

  public AntFarm getFarm()
  {
    return farm;
  }

  public void setFarm(AntFarm farm)
  {
    this.farm = farm;
  }

}
