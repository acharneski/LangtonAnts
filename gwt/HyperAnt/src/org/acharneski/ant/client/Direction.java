package org.acharneski.ant.client;

import org.acharneski.ant.client.Ant.Orientation;

public enum Direction
{
  Straight
  {
    @Override
    Orientation transform(Orientation from)
    {
      return from;
    }
  },
  Clockwise
  {
    @Override
    Orientation transform(Orientation from)
    {
      return from.clockwise();
    }
  },
  Counterclockwise
  {
    @Override
    Orientation transform(Orientation from)
    {
      return from.counterClockwise();
    }
  };
  
  public static Direction getDirection(char c)
  {
    Direction direction;
    if ('R' == c)
    {
      direction = Direction.Counterclockwise;
    }
    else if ('L' == c)
    {
      direction = Direction.Clockwise;
    }
    else if ('S' == c)
    {
      direction = Direction.Straight;
    }
    else
    {
      throw new RuntimeException("Unknown code character: " + c);
    }
    return direction;
  }

  abstract Orientation transform(Orientation from);
}