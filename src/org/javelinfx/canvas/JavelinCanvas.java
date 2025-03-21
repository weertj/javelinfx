package org.javelinfx.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.javelinfx.animation.JavelinAnimationTimer;

public class JavelinCanvas implements IJavelinCanvas {

  private static final long UPDATE_INTERVAL_NS = 1000000000L;

  private final Canvas                mCanvas;
  private final GraphicsContext       mContext;
  private final JavelinAnimationTimer mTimer;

  private       Color             mBgColor = Color.BLACK;
  private       boolean           mRender = true;
  private       boolean           mShowFPS = true;

  private       long              mRenderCounter = 0;
  private       long              mFrameCount = 0;
  private       long              mLastUpdateTime = 0;
  private       double            mFps = 0;

  public JavelinCanvas() {
    mCanvas = new Canvas(getDefaultWidth(), getDefaultHeight());
    mContext = mCanvas.getGraphicsContext2D();
    mContext.setImageSmoothing(imageSmoothing());
    mTimer = JavelinAnimationTimer.of();
    mTimer.addRunner(() -> render());
    return;
  }

  @Override
  public void close() {
    mTimer.close();
    return;
  }

  @Override
  public double getDefaultWidth() {
    return 400;
  }

  @Override
  public double getDefaultHeight() {
    return 600;
  }

  @Override
  public boolean imageSmoothing() {
    return true;
  }

  @Override
  public GraphicsContext context() {
    return mContext;
  }

  @Override
  public Canvas canvas() {
    return mCanvas;
  }

  @Override
  public void refresh() {
    return;
  }

  @Override
  public void start() {
    refresh();
//    if (mEventHandler==null) {
//      mEventHandler = FX_EventHandler.getOrCreate(mCanvas);
//      mEventHandler.addEventCallback(mCanvas, this);
//    }
    mTimer.start();
    return;
  }

  @Override
  public void stop() {
//    if (mEventHandler!=null) {
//      mEventHandler.removeEventCallback(mCanvas, this);
//    }
    mTimer.stop();
    return;
  }


  @Override
  public void render() {
    if (!mRender) return;

    renderBackground();

    if (mShowFPS) {
      showFPS();
    }

    return;
  }

  protected void renderBackground() {
    if (mBgColor!=null) {
      mContext.setFill(mBgColor);
      mContext.fillRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
    }
    return;
  }

  protected void showFPS() {
    mFrameCount++;
    long now = System.nanoTime();
    if (now - mLastUpdateTime >= UPDATE_INTERVAL_NS) {
      mFps = (double) mFrameCount / ((double) (now - mLastUpdateTime) / 1000000000.0);
      mFrameCount = 0;
      mLastUpdateTime = now;
    }
    // **** Render the FPS text on the canvas
    mContext.setFill(Color.GRAY);
    mContext.fillText(String.format("%.2f FPS", mFps), 10, 20);
    return;
  }

}