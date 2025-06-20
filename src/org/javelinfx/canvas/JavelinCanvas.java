package org.javelinfx.canvas;

import glc.GLC_Positions;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventTarget;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import org.javelinfx.animation.JavelinAnimationTimer;
import org.javelinfx.events.EH_Select;
import org.javelinfx.events.EventHandler;
import org.javelinfx.events.IEventHandler;
import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.ISP_Position;
import org.javelinfx.spatial.SP_Position;
import org.javelinfx.units.EUDistance;
import org.javelinfx.units.IU_Unit;
import org.javelinfx.window.S_Pointer;
import org.jgalaxy.gui.Global;
import org.jgalaxy.units.IJG_Unit;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavelinCanvas implements IJavelinCanvas, IJavelinUIElement {

  private static final long UPDATE_INTERVAL_NS = 1000000000L;

  private final Canvas                mCanvas;
  private final GraphicsContext       mContext;
  private final JavelinAnimationTimer mTimer;

  private       Color             mBgColor = Color.RED;
  private       boolean           mRender = true;
  private       boolean           mShowFPS = true;

  private       long              mRenderCounter = 0;
  private       long              mFrameCount = 0;
  private       long              mLastUpdateTime = 0;
  private       double            mFps = 0;

  private       IU_Unit           mBaseUnit = EUDistance.KM;
  private       double            mPixelModifier = 1.0;
  private       double            mPixelModifierCalc = 0.001;
  private       double            mOffsetPixelX = 0;
  private       double            mOffsetPixelY = 0;
  private final DoubleProperty    mPixelZoom = new SimpleDoubleProperty(200);
  private final DoubleProperty    mMoveSpeed = new SimpleDoubleProperty(0.033);
  private final ObjectProperty<ISP_Position>
                                  mCenter = new SimpleObjectProperty<>(SP_Position.of(0,0, EUDistance.PIXELS));

  private       double            mCurrentSmoothFactor = 20;
  private       List<Object>      mCurrentOverObject = new ArrayList<>();

  private       IEventHandler     mEventHandler;

  private       IJL_PlayerContext mPlayerContext;

  private final List<Runnable>    mRenderRunnables = new ArrayList<>();

  public JavelinCanvas() {
    mCanvas = new Canvas(getDefaultWidth(), getDefaultHeight());
    mContext = mCanvas.getGraphicsContext2D();
    mContext.setImageSmoothing(imageSmoothing());
    mTimer = JavelinAnimationTimer.of();
    mTimer.addRunner(() -> render());
    mCanvas.setOnScroll( pEvent -> {
      double deltay = pEvent.getDeltaY();
      if (deltay!=0.0) {
        double currentzoom = getPixelZoom();
        setPixelZoom( currentzoom + (currentzoom/10) * (pEvent.getDeltaY()/100.0) );
      }
    });
    mCanvas.setOnMouseDragged(pEvent -> {
      double x = pEvent.getX();
      double y = pEvent.getY();
//      mOffsetPixelX = x;
//      mOffsetPixelY = y;
    });
    return;
  }

  @Override
  public String id() {
    return "JavelinCanvas";
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
  public ObjectProperty<ISP_Position> center() {
    return mCenter;
  }

  @Override
  public void pixelModifier(double pPixelsPer1Unit, EUDistance pUnit) {
    mBaseUnit = pUnit;
    mPixelModifier = pPixelsPer1Unit;
    mPixelModifierCalc = mPixelModifier*(1/EUDistance.PIXELS_X.base());
    return;
  }

  protected double zoomLimitLower() {
    return -1;
  }

  protected double zoomLimitUpper() {
    return -1;
  }

  @Override
  public DoubleProperty pixelZoomProperty() {
    return mPixelZoom;
  }

  @Override
  public void setPixelZoom(double pZoom) {
    if (zoomLimitLower()>-1 && pZoom<zoomLimitLower()) {
      pZoom = zoomLimitLower();
    }
    if (zoomLimitUpper()>-1 && pZoom>zoomLimitUpper()) {
      pZoom = zoomLimitUpper();
    }
    mPixelZoom.set(pZoom);
    return;
  }

  @Override
  public double getPixelZoom() {
    return mPixelZoom.get();
  }

//  public double convertToPixelX(double pX, EUDistance pUnit ) {
////    return getPixelZoom()*mBaseUnit.convertTo( pX, EDistance.PIXELS_X ) * mPixelModifierCalc * pUnit.base();
//    return 0.0;
//  }

  private double convertToPixelX( double pX, EUDistance pUnit ) {
    return getPixelZoom()*mBaseUnit.convertTo( pX, EUDistance.PIXELS_X ) * mPixelModifierCalc * pUnit.base();
  }
  private double convertToPixelY( double pY, EUDistance pUnit ) {
    return getPixelZoom()*mBaseUnit.convertTo( pY, EUDistance.PIXELS_Y ) * mPixelModifierCalc * pUnit.base();
  }


  @Override
  public double toPixelX(double pX, EUDistance pUnit) {
    double x = pUnit.convertTo( pX, mCenter.get().xyUnit() )- mCenter.get().x();
    return mOffsetPixelX +
      (mCanvas.getWidth()/2 +
        convertToPixelX(x, pUnit)
      );
  }
  @Override
  public double toPixelY(double pY, EUDistance pUnit) {
    double y = mCenter.get().y() - pUnit.convertTo( pY, mCenter.get().xyUnit() );
    return mOffsetPixelY +
      (mCanvas.getHeight()/2 +
        convertToPixelY(y, pUnit)
      );
  }

  @Override
  public double fromPixelX(double pX, EUDistance pUnit) {
    double rrx = (pX - mOffsetPixelX - (mCanvas.getWidth() / 2)) / (getPixelZoom() * mPixelModifierCalc * pUnit.base());
    rrx = EUDistance.PIXELS_X.convertTo(rrx, pUnit) + mCenter.get().x();
    return rrx;
  }

  @Override
  public double fromPixelY(double pY, EUDistance pUnit) {
    double rry = (pY - mOffsetPixelY - (mCanvas.getHeight() / 2)) / (getPixelZoom() * mPixelModifierCalc * pUnit.base());
    rry = mCenter.get().y() - EUDistance.PIXELS_Y.convertTo(rry, pUnit);
    return rry;
  }


  @Override
  public void refresh() {
    return;
  }

  @Override
  public void start() {
    refresh();
    if (mEventHandler==null) {
      mEventHandler = EventHandler.of(mCanvas);
      mEventHandler.addEventCallback(mCanvas, this);
    }
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

  synchronized protected void renderItems( int pLevel, List<IJavelinRenderItem> pRenderItems, boolean pSpatialOffset, Map<String, Integer> pSpatials ) {
//    synchronized (pRenderItems) {

      var selpos = Global.getSelectedEntities().stream()
        .filter(IJG_Unit.class::isInstance)
        .map(e -> GLC_Positions.toString((IJG_Unit) e) )
        .toList();

      Map<String,Integer> possize = HashMap.newHashMap(16);
      if (pSpatialOffset) {
        for (IJavelinRenderItem item : pRenderItems) {
          String pos = GLC_Positions.toString(item.position());
          if (possize.containsKey(pos)) {
            possize.put(pos, possize.get(pos) + 1);
          } else {
            possize.put(pos, 1);
          }
        }
      }

      for (IJavelinRenderItem item : pRenderItems) {
        if (pSpatialOffset) {
          String pos = GLC_Positions.toString(item.position());
          boolean select = selpos.contains(pos);
          int itemseq = 0;
          if (pSpatials.containsKey(pos)) {
            itemseq = pSpatials.get(pos) + 1;
          }
          pSpatials.put(pos, itemseq);
          item.calculateRenderPositions(this,select, 0, itemseq, possize.get(pos));
        } else {
          item.calculateRenderPositions(this,false, 0, -1, 0);
        }
        item.render(this, mPlayerContext);
      }
//    }
    return;
  }

  /**
   * render
   */
  @Override
  public void render() {
    try {
      for (Runnable run : mRenderRunnables) {
        run.run();
      }
      if (!mRender) return;
      renderBackground();

      if (mPlayerContext != null) {
        Map<String, Integer> spatials = new HashMap<>(256);
        renderItems(0, mPlayerContext.renderItems(0), false, spatials);
        renderItems(1, mPlayerContext.renderItems(1), false, spatials);
        renderItems(2, mPlayerContext.renderItems(2), true, spatials);
        renderItems(3, mPlayerContext.renderItems(3), true, spatials);
        renderItems(4, mPlayerContext.renderItems(4), true, spatials);
        renderItems(5, mPlayerContext.renderItems(5), true, spatials);
        renderItems(6, mPlayerContext.renderItems(6), false, spatials);
        renderItems(7, mPlayerContext.renderItems(7), false, spatials);
      }

      if (mShowFPS) {
        showFPS();
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return;
  }

  @Override
  public void addCanvasRunnable(Runnable pRunnable) {
    if (!mRenderRunnables.contains(pRunnable)) {
      mRenderRunnables.add(pRunnable);
    }
    return;
  }

  @Override
  public void removeCanvasRunnable(Runnable pRunnable) {
    mRenderRunnables.remove(pRunnable);
    return;
  }

  @Override
  public void setPlayerContext(IJL_PlayerContext pContext) {
    mPlayerContext = pContext;
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
    mContext.fillText(String.format("%.2f FPS", mFps), 200, 20);
    return;
  }


  @Override
  public void moved(IEventHandler pEventHandler, ISP_Position pLastPosition, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pSource, EventTarget pTarget) {
    if (pPointer==S_Pointer.POINTER.PRIMARY) {
//      mOffsetPixelX += pPosition.x()-pLastPosition.x();
//      mOffsetPixelY += pPosition.y()-pLastPosition.y();
      double movespeed = mMoveSpeed.get() / mPixelZoom.get();
      center().set(center().get().add(
        -(pPosition.x()-pLastPosition.x())*movespeed,
        (pPosition.y()-pLastPosition.y())*movespeed));
      mCurrentSmoothFactor = 1;
    } else if (pPointer==S_Pointer.POINTER.NONE) {
      if (mPlayerContext!=null) {
        List<IJavelinUIElement> elems = mPlayerContext.retrieveByArea(pPosition);
        for( Object coo : new ArrayList<>(mCurrentOverObject)) {
          if (elems.contains(coo)) {

          } else {
            mCurrentOverObject.remove(coo);
            if (coo instanceof IJavelinUIElement fue) {
              fue.pointerLeft( this,mPlayerContext, pLastPosition);
            }
          }
        }
        if (elems.isEmpty()) {
          mCanvas.setCursor(Cursor.DEFAULT);
        } else {
          IJavelinUIElement topElement = elems.getLast();
          if (mCurrentOverObject.contains(topElement)) {
            topElement.pointerMoved( this,mPlayerContext, pPointer, pLastPosition);
          } else {
            if (!mCurrentOverObject.contains(topElement)) {
              mCurrentOverObject.add(topElement);
              topElement.pointerEntered( this,mPlayerContext, pPointer, pLastPosition);
            }
          }
        }
      }
    }
    return;
  }

  @Override
  public void pressed(IEventHandler pEventHandler, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pSource, EventTarget pTarget) {
    if (mPlayerContext!=null && (pPointer==S_Pointer.POINTER.PRIMARY || pPointer==S_Pointer.POINTER.SECONDARY)) {
      List<IJavelinUIElement> elems = mPlayerContext.retrieveByArea( pPosition );
      if (elems.isEmpty()) {
        pointerPressed(this,mPlayerContext, pPointer, pPosition);
      } else {
        elems.getLast().pointerPressed( this,mPlayerContext, pPointer, pPosition );
        if (pPointer==S_Pointer.POINTER.PRIMARY) {
          mPlayerContext.selectedItems().applyItem(EH_Select.ON, elems.getLast());
        }
      }
    }
    return;
  }


  @Override
  public void uiMap(IEventHandler pEventHandler, UIMAP pMap, double pValue) {
    if (pMap==UIMAP.VERTICALLIST_SCROLL) {
      double currentzoom = getPixelZoom();
      setPixelZoom( currentzoom - (currentzoom/10) * (pValue/100.0) );
    }
    return;
  }

  @Override
  public boolean mouseWheel(IEventHandler pEventHandler, ScrollEvent pEvent) {
    double deltay = pEvent.getDeltaY();
//    System.out.println(pixelZoomProperty().get());
    if (deltay!=0.0) {
      double currentzoom = getPixelZoom();
      mCurrentSmoothFactor = 1;
      setPixelZoom( currentzoom + (currentzoom/10) * (pEvent.getDeltaY()/100.0) );
    }
    return true;
  }


  // ****


  @Override
  public ISP_Position position() {
    throw new UnsupportedOperationException();

  }

  @Override
  public void setPosition(ISP_Position pPosition) {
    throw new UnsupportedOperationException();

  }

  @Override
  public Rectangle2D getOutline() {
    throw new UnsupportedOperationException();

  }

  @Override
  public void setOutline(Rectangle2D pOutline) {
    throw new UnsupportedOperationException();

  }

  @Override
  public Object element() {
    throw new UnsupportedOperationException();

  }

  @Override
  public List<IJavelinRenderItem> children() {
    throw new UnsupportedOperationException();

  }

  @Override
  public void render(IJavelinCanvas pCanvas, IJL_PlayerContext pContext) {
    throw new UnsupportedOperationException();

  }



}