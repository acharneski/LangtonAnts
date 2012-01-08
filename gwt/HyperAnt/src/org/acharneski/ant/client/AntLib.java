package org.acharneski.ant.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.PopupPanel;

public class AntLib
{
  public static class AntDialog
  {

    private final Canvas canvas;
    private final GwtAntFarm farm;
    private final PopupPanel popup;
    private final HandlerRegistration scrollRegistration;
    private final HandlerRegistration clickRegistration;
    private Timer timer;

    public AntDialog(int width, int height, Canvas canvas, String code)
    {
      this.canvas = canvas;
      canvas.setWidth(width + "px");
      canvas.setHeight(height + "px");
      canvas.setCoordinateSpaceWidth(width);
      canvas.setCoordinateSpaceHeight(height);
      this.popup = new PopupPanel();
      popup.setPixelSize(width, height);
      popup.add(canvas);
      this.scrollRegistration = Window.addWindowScrollHandler(new ScrollHandler()
      {
        @Override
        public void onWindowScroll(ScrollEvent event)
        {
          popup.center();
        }
      });
      this.clickRegistration = canvas.addClickHandler(new ClickHandler()
      {
        @Override
        public void onClick(ClickEvent event)
        {
          AntDialog.this.close();
        }
      });
      this.farm = new GwtAntFarm(canvas);
      Ant ants[] = {
          new Turnite(farm.width / 2, farm.height / 2, code)
      };
      for (Ant ant : ants)
      {
        farm.add(ant);
      }
    }

    protected void close()
    {
      this.timer.cancel();
      AntDialog.this.canvas.setVisible(false);
      popup.hide();
      popup.removeFromParent();
      scrollRegistration.removeHandler();
      clickRegistration.removeHandler();
    }

    public void show()
    {
      popup.center();
      popup.show();
      farm.stepsBetweenClear = 0;
      this.timer = farm.start();
    }

  }

  public static void antPopup(String code)
  {
    int width = (int) (Window.getClientWidth() * 0.9);
    int height = (int) (Window.getClientHeight() * 0.9);
    final Canvas canvas = Canvas.createIfSupported();
    if (canvas != null)
    {
      new AntDialog(width, height, canvas, code).show();
    }
  }

  public static native void exportStaticMethod() /*-{
		$wnd.antPopup = $entry(@org.acharneski.ant.client.AntLib::antPopup(Ljava/lang/String;));
  }-*/;
}
