package org.javelinfx.example;

import org.javelinfx.canvas.IJavelinCanvas;
import org.javelinfx.engine.JMainInterface;

public class SimpleMainInterface extends JMainInterface {

//  private IJavelinCanvas mCanvas = new SimpleCanvas();

  @Override
  public String title() {
    return "Simple Main Interface";
  }

  @Override
  public void init() {
    super.init();
    IJavelinCanvas canvas = new SimpleCanvas();
    add( canvas );
    canvas.canvas().setLayoutX( 10 );
    canvas.canvas().setLayoutY( 10 );
    mainPane().getChildren().add( canvas.canvas() );
    return;
  }

}
