package org.acharneski.ant;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import org.acharneski.ant.AwtAntFarm.AwtAntFarmEvents;

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

  AntFrame(AwtAntFarm farm, String rule) throws HeadlessException
  {
    super("Langton Ant: " + rule);
    this.farm = farm;
    this.farm.events = new AwtAntFarmEvents()
    {
      
      @Override
      public void onChange()
      {
        repaint();
      }
    };
    addWindowListener(new WindowEventHandler());
  }

  @Override
  public void paint(Graphics g)
  {
    g.drawImage(farm.image, 0, 0, null);
  }
}