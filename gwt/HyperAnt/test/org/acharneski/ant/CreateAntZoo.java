package org.acharneski.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acharneski.ant.client.Turnite;
import org.junit.Test;

public class CreateAntZoo
{
  public static class Dedup
  {
    public final int histogram[];

    public Dedup(int[] histogram)
    {
      super();
      this.histogram = histogram;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(histogram);
      return result;
    }

    @Override
    public boolean equals(Object obj)
    {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Dedup other = (Dedup) obj;
      if (!Arrays.equals(histogram, other.histogram))
        return false;
      return true;
    }
  }
  
  private File zooDir;
  private PrintStream htmlOut;
  private final Set<Dedup> dedupSet = new HashSet<CreateAntZoo.Dedup>();
  int imageIdx = 0;
  final int width = 190;
  final int height = 210;
  final int generations = 100000;
  boolean inlineSrc = true;
  String reportName = "zoo";
  File baseDir = new File("war/");

  @Test
  public void test() throws InterruptedException, IOException
  {
    List<Turnite> ants = generateAnts();
    reportAnts(ants);
  }

  private void reportAnts(List<Turnite> ants) throws FileNotFoundException, IOException
  {
    if (!inlineSrc)
    {
      this.zooDir = new File(baseDir, reportName);
      zooDir.mkdirs();
    }
    this.htmlOut = new PrintStream(new File(baseDir, reportName + ".html"));
    try
    {
      htmlOut.println("<!doctype html>" +
      		"<html>" +
      		"<head>\r\n" + 
      		"    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n" + 
      		"    <title>Langton Ants in HTML5</title>\r\n" + 
      		"    <link type=\"text/css\" rel=\"stylesheet\" href=\"HyperAnt.css\" />\r\n" + 
      		"    <script type=\"text/javascript\" language=\"javascript\" src=\"hyperant/hyperant.nocache.js\"></script>\r\n" + 
      		"  </head>\r\n" + 
      		"");
      htmlOut.println("<body>");
      for(Turnite ant : ants)
      {
        addAnt(reportName, ant);
      }
      htmlOut.println("</body>");
      htmlOut.println("</html>");
    } 
    finally
    {
      htmlOut.close();
    }
  }

  private List<Turnite> generateAnts()
  {
    List<Turnite> ants = new ArrayList<Turnite>();
    for(int states=1;states<=5;states++)
    {
      for(int colors=2;colors<=5;colors++)
      {
        if((states * colors) <= 8)
        {
          addTurnites(ants, colors, states);
        }
      }
    }
    return ants;
  }

  private void addTurnites(List<Turnite> ants, int colors, int states)
  {
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
          sb.append("/");
        }
      }
      Turnite ant = new Turnite(width/2, height/2, sb.toString());
      ants.add(ant);
    }
  }

  private void addAnt(String zooName, Turnite ant) throws IOException
  {
    final AwtAntFarm farm = new AwtAntFarm(width, height);
    farm.add(ant);
    farm.run(generations);
    double fillRatio = farm.fillRatio();
    if(fillRatio < 0.01) return;
    Dedup key = new Dedup(farm.histogram);
    if(dedupSet.contains(key)) return;
    dedupSet.add(key);
    
    String imageSrc;
    if (inlineSrc)
    {
      imageSrc = farm.write(); 
    }
    else
    {
      String imageName = ++imageIdx + ".png";
      farm.write(new File(zooDir, imageName));
      imageSrc = zooName + "/" + imageName;
    }
    htmlOut.println(String.format("<img src=\"%s\" onclick=\"antPopup('%s')\" alt=\"%s\" />", imageSrc, ant.code, ant.toString()));
  }
}
