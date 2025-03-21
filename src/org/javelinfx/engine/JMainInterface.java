package org.javelinfx.engine;

import javafx.scene.layout.AnchorPane;

public class JMainInterface implements IJMainInterface {

  private AnchorPane mMainPane;

  public JMainInterface() {
    mMainPane = new AnchorPane();
    return;
  }

  @Override
  public AnchorPane mainPane() {
    return mMainPane;
  }

  @Override
  public void init() {
  }

  @Override
  public void start() {
  }

  @Override
  public void stop() {
  }

  @Override
  public String title() {
    return "<empty>";
  }

}
