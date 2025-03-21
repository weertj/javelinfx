package org.javelinfx.example;

import org.javelinfx.canvas.IJavelinCanvas;
import org.javelinfx.engine.JMainInterface;

public class SimpleMainInterface extends JMainInterface {

  private IJavelinCanvas mCanvas = new SimpleCanvas();

  @Override
  public String title() {
    return "Simple Main Interface";
  }

  @Override
  public void init() {
    super.init();
    mCanvas = new SimpleCanvas();
    mCanvas.canvas().setLayoutX( 10 );
    mCanvas.canvas().setLayoutY( 10 );
    mainPane().getChildren().add( mCanvas.canvas() );
    return;
  }

  @Override
  public void start() {
    super.start();
    mCanvas.start();
    return;
  }

  @Override
  public void stop() {
    super.stop();
    mCanvas.stop();
    return;
  }
}
