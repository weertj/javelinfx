package org.javelinfx.engine;

import javafx.scene.layout.AnchorPane;
import org.javelinfx.canvas.IJavelinCanvas;
import org.javelinfx.player.IJL_PlayerContext;

import java.util.ArrayList;
import java.util.List;

public class JMainInterface implements IJMainInterface {

  private final AnchorPane            mMainPane;
  private final List<IJavelinCanvas>  mCanvas = new ArrayList<>(4);
  private       IJL_PlayerContext     mPlayerContext;

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
    return;
  }

  @Override
  public void start() {
    mCanvas.forEach( IJavelinCanvas::start );
    return;
  }

  @Override
  public void stop() {
    mCanvas.forEach( IJavelinCanvas::stop );
    return;
  }

  @Override
  public String title() {
    return "<empty>";
  }

  @Override
  public void add( IJavelinCanvas pCanvas ) {
    mCanvas.add( pCanvas );
    return;
  }

  @Override
  public void remove( IJavelinCanvas pCanvas ) {
    mCanvas.remove( pCanvas );
    return;
  }

  @Override
  public void setPlayerContext(IJL_PlayerContext pContext) {
    mPlayerContext = pContext;
    mCanvas.forEach( canvas -> canvas.setPlayerContext(pContext) );
    return;
  }

  @Override
  public IJL_PlayerContext getPlayerContext() {
    return mPlayerContext;
  }
}
