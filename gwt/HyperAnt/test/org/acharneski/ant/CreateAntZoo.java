package org.acharneski.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.acharneski.ant.client.Ant;
import org.acharneski.ant.client.RLCodeAnt;
import org.acharneski.ant.client.Turnite;
import org.junit.Test;

public class CreateAntZoo
{
  private File outDir;
  private PrintStream htmlOut;
  int imageIdx = 0;
  final int width = 190;
  final int height = 210;
  final int generations = 100000;

  @Test
  public void test() throws InterruptedException, IOException
  {
    List<Ant> ants = generateAnts();
    reportAnts(ants);
  }

  private void reportAnts(List<Ant> ants) throws FileNotFoundException, IOException
  {
    this.outDir = new File("site");
    outDir.mkdirs();
    this.htmlOut = new PrintStream(new File(outDir, "zoo.html"));
    try
    {
      htmlOut.println("<html>");
      htmlOut.println("<body>");
      for(Ant ant : ants)
      {
        addAnt(ant);
      }
      htmlOut.println("</body>");
      htmlOut.println("</html>");
    } 
    finally
    {
      htmlOut.close();
    }
  }

  private List<Ant> generateAnts()
  {
    List<Ant> ants = new ArrayList<Ant>();
    for(int s=1;s<=4;s++)
    {
      for(int c=2;c<=3;c++)
      {
        if((s * c) <= 8)
        {
          addTurnites(ants, c, s);
        }
      }
    }
    return ants;
  }

  private void addTurnites(List<Ant> ants, int colors, int states)
  {
    //Math.log(m)/Math.log(2)
    int m = (int) Math.pow(2, colors * states);
    for(int i=0;i<m;i++)
    {
      StringBuilder sb = new StringBuilder();
      DataSource source = new DataSource(i, m);
      int chars = 0;
      while(source.hasNext(2))
      {
        chars++;
        if(0 == source.getNext(2))
        {
          sb.append("R");
        }
        else
        {
          sb.append("L");
        }
        if(0 == (chars % colors) && source.hasNext(2))
        {
          sb.append("\n");
        }
      }
      Turnite ant = new Turnite(width/2, height/2, sb.toString());
      ants.add(ant);
    }
  }

  private void addRLAnts(List<Ant> ants, int m)
  {
    for(int i=0;i<m;i++)
    {
      StringBuilder sb = new StringBuilder();
      DataSource source = new DataSource(i, m);
      while(source.hasNext(2))
      {
        if(0 == source.getNext(2))
        {
          sb.append("R");
        }
        else
        {
          sb.append("L");
        }
      }
      RLCodeAnt ant = new RLCodeAnt(width/2, height/2, sb.toString());
      ants.add(ant);
    }
  }

  private void addAnt(Ant ant) throws IOException
  {
    final AwtAntFarm farm = new AwtAntFarm(width, height);
    farm.add(ant);
    farm.run(generations);
    String imageName = ++imageIdx + ".jpg";
    farm.write(new File(outDir, imageName));
    //displayModal(farm, rule);
    htmlOut.println("<span>");
    htmlOut.println(String.format("<img src=\"%s\" alt=\"%s\" />", imageName, ant.toString()));
    htmlOut.println("</span>");
  }

  private void displayModal(final AwtAntFarm farm, String rule) throws InterruptedException
  {
    final AntFrame frame = new AntFrame(farm, rule);
    frame.setSize(800, 600);
    frame.setVisible(true);
    frame.onClose.acquire();
  }
}
