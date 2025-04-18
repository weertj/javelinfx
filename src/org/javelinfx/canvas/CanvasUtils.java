package org.javelinfx.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class CanvasUtils {

  static final private Stop STOP0 = new Stop(1, Color.TRANSPARENT);

  static public void drawGradientLine(GraphicsContext gc, double x1, double y1, double x2, double y2, Color color) {
    LinearGradient gradient = new LinearGradient(
      x1, y1, x2, y2, false, CycleMethod.NO_CYCLE,
      new Stop(0.0, color),new Stop(0.4, color),STOP0

    );
    gc.setStroke(gradient);
    gc.strokeLine(x1, y1, x2, y2);
    return;
  }

  private CanvasUtils() {
    return;
  }

}
