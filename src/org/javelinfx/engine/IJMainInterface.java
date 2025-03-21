package org.javelinfx.engine;

import javafx.scene.layout.AnchorPane;

public interface IJMainInterface {

  AnchorPane mainPane();

  String title();

  void init();

  void start();
  void stop();

}
