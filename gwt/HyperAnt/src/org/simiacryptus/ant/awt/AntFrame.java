package org.simiacryptus.ant.awt;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import org.simiacryptus.ant.awt.AwtAntFarm.AwtAntFarmEvents;

@SuppressWarnings("serial")
public final class AntFrame extends JFrame
{
  private final class WindowEventHandler implements WindowListener
  {
    @Override
    public void windowOpened(WindowEvent arg0)
    {
    }

    @Override
    public void windowIconified(WindowEvent arg0)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent arg0)
    {
    }

    @Override
    public void windowClosing(WindowEvent arg0)
    {
      onClose.release();
    }

    @Override
    public void windowClosed(WindowEvent arg0)
    {
    }

    @Override
    public void windowActivated(WindowEvent arg0)
    {
    }
  }

  public final Semaphore onClose = new Semaphore(0);
  private AwtAntFarm farm;
  private Semaphore onRepaint = new Semaphore(0);
  public int speed = 40;

  public AntFrame(AwtAntFarm farm, String rule) throws HeadlessException
  {
    super("Langton Ant: " + rule);
    this.farm = farm;
    this.farm.events = new AwtAntFarmEvents()
    {
      
      @Override
      public void onChange()
      {
        repaint();
        try
        {
          if(0 < speed) onRepaint.acquire();
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    };
    addWindowListener(new WindowEventHandler());
  }

  @Override
  public void paint(Graphics g)
  {
    g.drawImage(farm.image, 0, 0, null);
    onRepaint.release(speed);
  }
}