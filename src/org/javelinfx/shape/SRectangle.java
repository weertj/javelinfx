package org.javelinfx.shape;

import java.awt.geom.Rectangle2D;

public class SRectangle {

  static public Rectangle2D shrink(Rectangle2D pRect, double pFactorX, double pFactorY, double pFactorWidth, double pFactorHeight ) {
    double nwidth = pRect.getWidth() * pFactorWidth;
    double nheight = pRect.getHeight() * pFactorHeight;
    return new Rectangle2D.Double(pRect.getMinX() + pRect.getWidth()*pFactorX, pRect.getMinY() + pRect.getHeight()*pFactorY, nwidth, nheight);
  }

  private SRectangle() {
    return;
  }

}
