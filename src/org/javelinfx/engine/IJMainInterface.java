package org.javelinfx.engine;

import javafx.scene.layout.AnchorPane;
import org.javelinfx.canvas.IJavelinCanvas;
import org.javelinfx.player.IJL_PlayerContext;

public interface IJMainInterface {

  AnchorPane mainPane();

  String title();

  void init();

  void start();
  void stop();

  void add( IJavelinCanvas pCanvas );
  void remove( IJavelinCanvas pCanvas );

  void setPlayerContext(IJL_PlayerContext pContext);
  IJL_PlayerContext getPlayerContext();


}
