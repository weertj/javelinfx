package org.javelinfx.stage;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class S_Stage {

  static public Stage stageFromPane(Pane pPane ) {
    if (pPane.getScene()!=null) {
      Object w = pPane.getScene().getWindow();
      if (w instanceof Stage) {
        return (Stage)w;
      }
    }
    return null;
  }


}
