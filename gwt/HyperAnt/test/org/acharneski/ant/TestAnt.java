package org.acharneski.ant;

import java.util.Random;

import org.acharneski.ant.client.Ant;
import org.junit.Test;

public class TestAnt
{
  @Test
  public void test() throws InterruptedException
  {
    final AntFrame frame = new AntFrame();
    final AwtAntFarm farm = new AwtAntFarm(frame);
    Random random = new Random();
    farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    farm.add(new Ant((int) (farm.width * random.nextDouble()), (int) (farm.height * random.nextDouble())));
    frame.setSize(800, 600);
    frame.setVisible(true);
    new Thread(farm).start();
    frame.onClose.acquire();
    farm.stop();
  }
}
