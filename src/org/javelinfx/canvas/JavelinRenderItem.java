package org.javelinfx.canvas;

import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class JavelinRenderItem implements IJavelinRenderItem {

  private String        mID;
  private Object        mElement;
  private ISP_Position  mPosition;
  private Rectangle2D   mOutline;

  protected JavelinRenderItem(String id, Object element, ISP_Position position) {
    mID = id;
    mElement = element;
    mPosition = position;
    return;
  }

  @Override
  public String id() {
    return mID;
  }

  @Override
  public void calculateRenderPositions(IJavelinCanvas pCanvas,double pRingMod, int pItemSeqNr, int pTotItems, double pSmoothFactor) {
    outline(pCanvas,pRingMod, pItemSeqNr, pTotItems, pSmoothFactor);
    return;
  }

  protected double defaultUnitSize() {
    return 10;
  }

  protected double minUnitSize() {
    return 10;
  }

  protected double maxUnitSize() {
    return 80;
  }

  private void outline( IJavelinCanvas pCanvas, double pRingMod, int pItemSeqNr, int pTotItems, double pSmoothFactor ) {
    if (position()!=null) {
      double x = pCanvas.toPixelX(position().x(), position().xyUnit());
      double y = pCanvas.toPixelY(position().y(), position().xyUnit());
      double unitSize = defaultUnitSize();
      if (zoomType() == ZOOMTYPE.WITHLIMITS) {
        unitSize *= pCanvas.getPixelZoom();
      }
      unitSize = Math.max(Math.min(unitSize, maxUnitSize()), minUnitSize());
      if (pItemSeqNr > 0) {
        double gap = 8;
        x += pItemSeqNr * Math.max(gap,2 * pCanvas.getPixelZoom());
        y += pItemSeqNr * Math.max(gap,2 * pCanvas.getPixelZoom());
//        x += pItemSeqNr * 2 * pCanvas.getPixelZoom();
//        y += pItemSeqNr * 2 * pCanvas.getPixelZoom();
//      ISP_Vector vec = SP_Vector.of( unitSize*radiusModFromOrigin()*pRingMod, SP_Angle.of( Math.PI*2*pItemSeqNr/pTotItems ));
//      x += vec.target().x();
//      y += vec.target().y();
      }

//    processXY( x, y, pSmoothFactor );
      Rectangle2D rect = new Rectangle2D.Double(x - unitSize, y - unitSize, unitSize, unitSize);
      setOutline(rect);
    }
    return;
  }


  @Override
  public Object element() {
    return mElement;
  }

  @Override
  public ISP_Position position() {
    return mPosition;
  }

  @Override
  public Rectangle2D getOutline() {
    return mOutline;
  }

  @Override
  public void setOutline(Rectangle2D pOutline) {
    mOutline = pOutline;
    return;
  }

  @Override
  public List<IJavelinRenderItem> children() {
    return List.of();
  }

  @Override
  public void render(IJavelinCanvas pCanvas, IJL_PlayerContext pContext) {
    return;
  }
}
