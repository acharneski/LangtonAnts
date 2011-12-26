package org.acharneski.ant;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

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
  public final BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

  AntFrame() throws HeadlessException
  {
    super("Langton Ants");
    addWindowListener(new WindowEventHandler());
  }

  @Override
  public void paint(Graphics g)
  {
    g.drawImage(image, 0, 0, null);
  }
}