package org.simiacryptus.ant.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.simiacryptus.ant.common.Turnite;
import org.simiacryptus.ant.zoo.ZooGenerator;

public class CreateAntZoo
{
  @Test
  public void turnites() throws InterruptedException, IOException
  {
    ZooGenerator zoo = new ZooGenerator();
    zoo.minStates = 2;
    zoo.minColors = 2;
    zoo.maxColors = 3;
    zoo.reportName = "turnites";
    List<Turnite> ants = zoo.generateAnts();
    zoo.reportAnts(ants);
  }

  @Test
  public void colors() throws InterruptedException, IOException
  {
    ZooGenerator zoo = new ZooGenerator();
    zoo.reportName = "colors";
    zoo.minStates = 1;
    zoo.maxStates = 1;
    zoo.maxColors = 8;
    List<Turnite> ants = zoo.generateAnts();
    zoo.reportAnts(ants);
  }
}
