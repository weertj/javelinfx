package org.javelinfx.window;

import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class S_Window {

  static public Window windowFromPane(Pane pPane ) {
    return pPane.getScene().getWindow();
  }

}
