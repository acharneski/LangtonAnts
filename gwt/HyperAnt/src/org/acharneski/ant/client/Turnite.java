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
  public String code;
  int state = 0;

  public Turnite(int x, int y, String code)
  {
    super(x, y);
    this.code = code;
    String[] lines = code.toUpperCase().split("\n|/");
    for(String line : lines)
    {
      ArrayList<Rule> innerList = new ArrayList<Turnite.Rule>();
      char[] ruleChars = line.toCharArray();
      for (char c : ruleChars)
      {
        Direction direction = Direction.getDirection(c);
        int newRule = (rules.size() + 1) % lines.length;
        int newColor = (innerList.size() + 1) % ruleChars.length;
        innerList.add(new Rule(direction, newColor, newRule));
      }
      rules.add(innerList);
    }
  }


  @Override
  public void step()
  {
    byte oldColor = getFarm().get(point);
    List<Rule> ruleSet = rules.get(state);
    Rule rule = ruleSet.get(oldColor % ruleSet.size());
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
    builder.append(code);
    //builder.append("]");
    return builder.toString();
  }
  
}
