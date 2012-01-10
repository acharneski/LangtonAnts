package org.simiacryptus.ant.zoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simiacryptus.ant.awt.AwtAntFarm;
import org.simiacryptus.ant.common.Turnite;

public class ZooGenerator
  {
    public File zooDir;
    private PrintStream htmlOut;
    private final Set<AntSignature> dedupSet = new HashSet<AntSignature>();
    public int imageIdx = 0;
    public int width = 100;
    public int height = 100;
    public int generations = 30000;
    public boolean inlineSrc = true;
    public String reportName = "zoo";
    public File baseDir = new File("war/");
    public int maxStates = 5;
    public int maxColors = 5;
    public int minStates = 1;
    public int minColors = 2;
    public int maxBits = 10;
    public double minFill = 0.01;


    public void reportAnts(List<Turnite> ants) throws FileNotFoundException, IOException
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

    public List<Turnite> generateAnts()
    {
      List<Turnite> ants = new ArrayList<Turnite>();
      for(int states=minStates;states<=maxStates;states++)
      {
        for(int colors=minColors;colors<=maxColors;colors++)
        {
          if((states * colors) <= maxBits)
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
        IndexedSequence source = new IndexedSequence(i, m);
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
      if(fillRatio < minFill) return;
      AntSignature key = new AntSignature(farm.histogram);
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