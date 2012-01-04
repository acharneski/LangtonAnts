package org.acharneski.ant;

import java.util.Random;

import org.acharneski.ant.client.Ant;
import org.acharneski.ant.client.RLCodeAnt;
import org.acharneski.ant.client.Turnite;
import org.junit.Test;

public class TestAnt
{
  @Test
  public void test() throws InterruptedException
  {
    final AwtAntFarm farm = new AwtAntFarm();
    final AntFrame frame = new AntFrame(farm, "Test");
    Random random = new Random();
    
    // Traditional langton ant
    //farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    
    // Slow growing "brain"
    farm.add(new RLCodeAnt(400, 300, "RRLL"));
    
    // Very slow growing "cell" figure
    //farm.add(new RLCodeAnt(400, 300, "RRLLR"));
    
    // Chaotically growing "marble"
    //farm.add(new RLCodeAnt(400, 300, "RLR"));
    
    // "Cyber-brain"... should be same as RRLL, but differing initial conditions!
    //farm.add(new RLCodeAnt(400, 300, "RLLR"));
    
    // "boxed" chaotic growth 
    //farm.add(new RLCodeAnt(400, 300, "RLLLRR"));
    
    // Vertical line
    //farm.add(new RLCodeAnt(400, 300, "LS"));
    
    // Straight lines wrapping around a chaotic core
    //farm.add(new RLCodeAnt(400, 300, "LRS"));
    
    // "Plaid" ant
    //farm.add(new RLCodeAnt(400, 300, "LLR"));
    
    // Fill then LRS
    //farm.add(new RLCodeAnt(400, 300, "SRL"));
    
    // Corner-filling chaotic growth
    //farm.add(new RLCodeAnt(400, 300, "SRRLL"));
    
    farm.add(new Turnite(400, 300, "RL\nLR"));
    
    
    //farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    //farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    frame.setSize(800, 600);
    frame.setVisible(true);
    new Thread(farm).start();
    frame.onClose.acquire();
    farm.stop();
  }
}
