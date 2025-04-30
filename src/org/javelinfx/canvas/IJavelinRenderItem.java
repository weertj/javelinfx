package org.javelinfx.canvas;

import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.ISP_Position;

import java.awt.geom.Rectangle2D;
import java.util.List;

public interface IJavelinRenderItem {

  enum ZOOMTYPE {
    WITHLIMITS,
    DEFAULT
  }

  default ZOOMTYPE zoomType() {
    return ZOOMTYPE.DEFAULT;
  }

  String id();

  default void calculateRenderPositions( IJavelinCanvas pCanvas, double pRingMod, int pItemSeqNr, int pTotItems, double pSmoothFactor ) {
    return;
  }

  ISP_Position position();
  void setPosition( ISP_Position pPosition );

  Rectangle2D getOutline();
  void setOutline( Rectangle2D pOutline );

  /**
   * The "guiless" element
   * @return
   */
  Object element();

  List<IJavelinRenderItem> children();

  void render(IJavelinCanvas pCanvas, IJL_PlayerContext pContext );

}
