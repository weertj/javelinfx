package org.javelinfx.engine;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

abstract public class JPanelInterface implements IJPanelInterface {

  private Stage mThisStage;

  public Stage getThisStage() {
    return mThisStage;
  }

  public void setThisStage(Stage pStage) {
    mThisStage = pStage;
  }

  @Override
  public AnchorPane rootPane() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
