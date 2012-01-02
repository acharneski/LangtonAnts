package org.acharneski.ant.client;

import java.util.ArrayList;
import java.util.List;


public class RLCodeAnt extends Ant
{
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

    abstract Orientation transform(Orientation from);
  }

  public static class Rule
  {
    public final Direction direction;
    public int newColor;

    public Rule(Direction direction, int newColor)
    {
      super();
      this.direction = direction;
      this.newColor = newColor;
    }
  }
  List<Rule> rules = new ArrayList<RLCodeAnt.Rule>();

  public RLCodeAnt(int x, int y, String code)
  {
    super(x, y);
    Rule lastRule = null;
    for (char c : code.toUpperCase().toCharArray())
    {
      if ('R' == c)
      {
        lastRule = new Rule(Direction.Counterclockwise, rules.size() + 1);
      }
      else if ('L' == c)
      {
        lastRule = new Rule(Direction.Clockwise, rules.size() + 1);
      }
      else if ('S' == c)
      {
        lastRule = new Rule(Direction.Straight, rules.size() + 1);
      }
      else
      {
        throw new RuntimeException("Unknown code character: " + c);
      }
      rules.add(lastRule);
    }
    lastRule.newColor = 0;
  }

  @Override
  public void step()
  {
    byte oldColor = getFarm().get(point);
    Rule rule = rules.get(oldColor % rules.size());
    orientation = rule.direction.transform(orientation);
    getFarm().set(point, (byte) rule.newColor);
    point = point.add(orientation);
  }

}
