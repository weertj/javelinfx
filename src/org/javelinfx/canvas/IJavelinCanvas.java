package org.javelinfx.canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.javelinfx.events.IEH_EventListener;
import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.ISP_Position;
import org.javelinfx.units.EUDistance;

import java.io.Closeable;

public interface IJavelinCanvas extends IEH_EventListener, Closeable {

  @Override
  void close();

  Canvas canvas();
  GraphicsContext context();

  ObjectProperty<ISP_Position> center();

  void pixelModifier( double pPixelsPer1Unit, EUDistance pUnit );
  DoubleProperty pixelZoomProperty();
  double     getPixelZoom();
  void       setPixelZoom( double pZoom );

  double toPixelX(double pX, EUDistance pUnit );
  double toPixelY(double pY, EUDistance pUnit );
//  double fromPixelX(double pX, EDistance pUnit );
//  double fromPixelY(double pY, EDistance pUnit );

  double getDefaultWidth();
  double getDefaultHeight();
  boolean imageSmoothing();

  void refresh();
  void start();
  void stop();

  void render();

  void setPlayerContext( IJL_PlayerContext pContext );

}
