package org.simiacryptus.ant.common;

import java.util.ArrayList;
import java.util.List;


public class RLCodeAnt extends Ant
{
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
  public String name;

  public RLCodeAnt(int x, int y, String code)
  {
    super(x, y);
    this.name = code;
    Rule lastRule = null;
    for (char c : code.toUpperCase().toCharArray())
    {
      Direction direction = Direction.getDirection(c);
      lastRule = new Rule(direction, rules.size() + 1);
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

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    //builder.append("RLCodeAnt [name=");
    builder.append(name);
    //builder.append("]");
    return builder.toString();
  }
  
}
