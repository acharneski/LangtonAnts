package org.acharneski.ant.client;

import java.util.ArrayList;
import java.util.List;


public class Turnite extends Ant
{
  public static class Rule
  {
    public final Direction direction;
    public int newColor;
    public int newRule;

    public Rule(Direction direction, int newColor, int newRule)
    {
      super();
      this.direction = direction;
      this.newColor = newColor;
      this.newRule = newRule;
    }
  }
  
  List<List<Rule>> rules = new ArrayList<List<Turnite.Rule>>();
  public String name;
  int state = 0;

  public Turnite(int x, int y, String code)
  {
    super(x, y);
    this.name = code;
    Rule lastRule = null;
    String[] lines = code.toUpperCase().split("\n");
    for(String line : lines)
    {
      ArrayList<Rule> innerList = new ArrayList<Turnite.Rule>();
      for (char c : line.toCharArray())
      {
        Direction direction = Direction.getDirection(c);
        lastRule = new Rule(direction, innerList.size() + 1, (rules.size() + 1) % lines.length);
        innerList.add(lastRule);
      }
      rules.add(innerList);
      lastRule.newColor = 0;
    }
  }


  @Override
  public void step()
  {
    byte oldColor = getFarm().get(point);
    Rule rule = rules.get(state).get(oldColor % rules.size());
    state = rule.newRule;
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
