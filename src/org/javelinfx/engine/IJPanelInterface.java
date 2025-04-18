package org.javelinfx.engine;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public interface IJPanelInterface {

  Stage getThisStage();
  void setThisStage(Stage pStage);
  AnchorPane rootPane();

}
