package org.javelinfx.image;

import org.javelinfx.colors.SColors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class GImage {

//  static private final Map<String,BufferedImage> IMAGETINTCACHE = HashMap.newHashMap(128);

  static public BufferedImage imageTintColorCache( BufferedImage pImage, java.awt.Color pFromColor, java.awt.Color pTintColor ) {
    BufferedImage image;
    if (pImage.getType()==BufferedImage.TYPE_INT_ARGB) {
      image = pImage;
    } else {
      image = new BufferedImage(pImage.getWidth(), pImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D graphics = image.createGraphics();
      graphics.drawImage(pImage, 0, 0, null);
      graphics.dispose();
    }
    int rgb     = pTintColor.getRGB() & 0x00ffffff;
    int width   = image.getWidth();
    int height  = image.getHeight();
    int fromRGB = SColors.toRGBInt(SColors.toFXColor(pFromColor));
    for( int y=0; y<height; y++ ) {
      for( int x=0; x<width; x++ ) {
        int col = image.getRGB( x, y );
        int alpha = col & 0xff000000;
        col      &= 0x00ffffff;
        if (alpha!=0x00000000 && col==fromRGB) {
          image.setRGB( x, y, alpha | rgb );
        }
      }
    }
    return image;
  }

  static public BufferedImage imageTintColor( BufferedImage pImage, java.awt.Color pFromColor, java.awt.Color pTintColor ) {
    return imageTintColorCache( pImage, pFromColor, pTintColor );
  }

}
