package org.javelinfx.image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class GIcon implements IIcon {

  static public final IIcon NULL = of( (Image)null,null );

  static public BufferedImage toBufferedImage(Image pImage ) {
    if (pImage.isBackgroundLoading()) {
      throw new UnsupportedOperationException("isBackgroundLoading");
    }
    if (pImage.isError()) {
      throw new UnsupportedOperationException("isError");
    }
    return SwingFXUtils.fromFXImage( pImage, null );
  }

  static public Image toImage( BufferedImage pImage ) {
    return SwingFXUtils.toFXImage( pImage, null );
  }


  static public Image convertToImage( Object pImage ) {
    if (pImage instanceof Image im) {
      return im;
    }
    if (pImage instanceof BufferedImage im) {
      return SwingFXUtils.toFXImage( im, null );
    }
    if (pImage instanceof ImageIcon icon) {
      return SwingFXUtils.toFXImage((BufferedImage)icon.getImage(), null);
    }
    throw new UnsupportedOperationException("Unsupported image type: " + pImage.getClass());
  }

  static public IIcon of( BufferedImage pBufImage, Color pBaseColor ) {
    return new GIcon( pBufImage, null, pBaseColor );
  }

  static public IIcon of( Image pImage, Color pBaseColor ) {
    return new GIcon( null, pImage, pBaseColor );
  }

  static public IIcon of( File pFile ) throws IOException {
    return of(pFile,null);
  }

  static public IIcon of( File pFile, Color pBaseColor ) throws IOException {
    if (pFile!=null && pFile.exists() && pFile.isFile()) {
      Image image = new Image("file:" + pFile.getCanonicalPath() );
      return of( image, pBaseColor );
    }
    throw new IOException( "File error: " + pFile);
  }

  static public IIcon of( InputStream pStream ) {
    Image image = new Image( pStream );
    return of( image, null );
  }

  private final Color         mBaseColor;
  private final BufferedImage mBufImage;
  private final Image         mImage;

  private GIcon( BufferedImage pBufImage, Image pImage, Color pBaseColor ) {
    mBaseColor  = pBaseColor;
    mBufImage   = pBufImage;
    mImage      = pImage;
    return;
  }

  @Override
  public boolean isEmpty() {
    return mBufImage==null && mImage==null;
  }

  @Override
  public double width() {
    if (mBufImage!=null) {
      return mBufImage.getWidth();
    }
    if (mImage!=null) {
      return mImage.getWidth();
    }
    return -1;
  }

  @Override
  public double height() {
    if (mBufImage!=null) {
      return mBufImage.getHeight();
    }
    if (mImage!=null) {
      return mImage.getHeight();
    }
    return -1;
  }

  @Override
  public Color baseColor() {
    return mBaseColor;
  }

  @Override
  public Optional<Image> image() {
    return Optional.of(mImage);
  }

  @Override
  public Optional<BufferedImage> bufferedImage() {
    return Optional.of(mBufImage);
  }

  @Override
  public BufferedImage getOrCreateBufferedImage() {
    if (mBufImage==null) {
      return toBufferedImage( mImage );
    } else {
      return mBufImage;
    }
  }

  @Override
  public Image getOrCreateImage() {
    if (mImage==null) {
      if (mBufImage==null) {
        return null;
      } else {
        return SwingFXUtils.toFXImage(mBufImage, null );
      }
    } else {
      return mImage;
    }
  }

  @Override
  public IIcon flip( FLIP pFlip ) {
    var bufImage = getOrCreateBufferedImage();
    AffineTransform tx;
    switch( pFlip ) {
      case NONE -> tx = AffineTransform.getScaleInstance( 1, 1 );
      case HORIZONTAL -> {
        tx = AffineTransform.getScaleInstance( -1, 1);
        tx.translate( -bufImage.getWidth(null), 0);
      }
      case VERTICAL   -> {  tx = AffineTransform.getScaleInstance(1, -1 );
        tx.translate( 0, -bufImage.getHeight(null));
      }
      case HORIZONTAL_AND_VERTICAL
        -> {  tx = AffineTransform.getScaleInstance(-1, -1 );
        tx.translate( -bufImage.getWidth(null), -bufImage.getHeight(null));
      }
      default         -> throw new UnsupportedOperationException();
    }
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    BufferedImage result = new BufferedImage( bufImage.getWidth(), bufImage.getHeight(), BufferedImage.TYPE_INT_ARGB );
    result = op.filter(bufImage, result );
    return of( result, mBaseColor );
  }

}
