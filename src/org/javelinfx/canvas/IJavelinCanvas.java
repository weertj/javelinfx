package org.javelinfx.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.Closeable;

public interface IJavelinCanvas extends Closeable {

  @Override
  void close();

  Canvas canvas();
  GraphicsContext context();

  double getDefaultWidth();
  double getDefaultHeight();
  boolean imageSmoothing();

  void refresh();
  void start();
  void stop();

  void render();

}
