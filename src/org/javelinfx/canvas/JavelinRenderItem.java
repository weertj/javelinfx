package org.javelinfx.canvas;

import javafx.scene.canvas.GraphicsContext;
import org.javelinfx.fonts.F_Fonts;
import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.ISP_Position;

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
  public void calculateRenderPositions(IJavelinCanvas pCanvas, boolean pParentSelect,double pRingMod, int pItemSeqNr, int pTotItems) {
    outline(pCanvas,pParentSelect,pRingMod, pItemSeqNr, pTotItems);
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

  protected double itemGapUnitSizeRatio() {
    return 0.2;
  }

  protected boolean stackingUnitInACircle() {
    return true;
  }

  /**
   * outline
   * @param pCanvas
   * @param pRingMod
   * @param pItemSeqNr
   * @param pTotItems
   */
  private void outline( IJavelinCanvas pCanvas, boolean pParentSelect, double pRingMod, int pItemSeqNr, int pTotItems ) {
    if (position()!=null) {
      double x = pCanvas.toPixelX(position().x(), position().xyUnit()) + xPixelOffset();
      double y = pCanvas.toPixelY(position().y(), position().xyUnit()) + yPixelOffset();
      double unitSize = defaultUnitSize();
      if (zoomType() == ZOOMTYPE.WITHLIMITS) {
        unitSize *= pCanvas.getPixelZoom();
      }
      unitSize = Math.clamp(unitSize, minUnitSize(), maxUnitSize());
      double offset = unitSize*0.5;
      if (pItemSeqNr > 0) {
        double gap;
        if (pParentSelect) {
          gap = itemGapUnitSizeRatio() * unitSize + unitSize;//itemGap();
          offset = 0;
        } else {
          gap = 4;
        }
        x += offset + pItemSeqNr * Math.max(gap,2 * pCanvas.getPixelZoom());
        y += offset + pItemSeqNr * Math.max(gap,2 * pCanvas.getPixelZoom());
      }
      Rectangle2D rect = new Rectangle2D.Double(x - unitSize/2, y - unitSize/2, unitSize, unitSize);
      setOutline(rect);
    }
    return;
  }

  protected double xPixelOffset() {
    return 0;
  }

  protected double yPixelOffset() {
    return 0;
  }

  protected void renderText(GraphicsContext pGC, String pFontID, double pX, double pY, String pText ) {
    pGC.setFont(F_Fonts.getDefault().fontByKey(pFontID));
//    pGC.setFill(Color.BLACK);
//    pGC.setLineWidth(1);
//    pGC.strokeText(pText, pX, pY );
//    pGC.setFill(Colors.canvasTextColor());
    pGC.fillText(pText, pX, pY);
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
  public void setPosition(ISP_Position pPosition) {
    mPosition = pPosition;
    return;
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
