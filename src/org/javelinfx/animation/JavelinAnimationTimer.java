package org.javelinfx.animation;

import javafx.animation.AnimationTimer;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavelinAnimationTimer extends AnimationTimer implements Closeable {

  static public JavelinAnimationTimer of() {
    return new JavelinAnimationTimer();
  }

  private final List<Runnable> mRunners = new ArrayList<>(8);
  private       int            mTargetFPS = 80;
  private       long           mnSecPerFrame = Math.round(1.0/mTargetFPS * 1e9)/2;
  private       long           mLastFrameTime = 0;

  private JavelinAnimationTimer() {
    super();
  }

  @Override
  public void close() {
    mRunners.clear();
    stop();
    return;
  }

  public void addRunner(Runnable pRunner ) {
    mRunners.add( pRunner );
    return;
  }

  @Override
  public void handle(long now) {
    if ((now-mLastFrameTime) >= mnSecPerFrame) {
      for (Runnable runner : new ArrayList<>(mRunners)) {
        runner.run();
      }
    }
    mLastFrameTime = now;
    return;
  }

}
