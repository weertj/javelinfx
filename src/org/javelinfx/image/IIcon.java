package org.javelinfx.image;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface IIcon {

  boolean isEmpty();
  enum FLIP {
    NONE,
    HORIZONTAL,
    VERTICAL,
    HORIZONTAL_AND_VERTICAL
  }

  double width();
  double height();

  Color baseColor();

  Optional<Image> image();
  Optional<BufferedImage> bufferedImage();

  Image         getOrCreateImage();
  BufferedImage getOrCreateBufferedImage();

  // **** Mutation methods
  IIcon flip( FLIP pFlip );

}
