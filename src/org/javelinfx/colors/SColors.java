package org.javelinfx.colors;

import javafx.scene.paint.Color;

public class SColors {

  static final public Color GREENERY  = Color.web("#92B558");

  static final public Color P1PHOSPHOR  = Color.rgb( 65,255,0);

  static final public Color CELESTIAL_YELLOW  = Color.web("#EDEAB1");
  static final public Color CHERRY_LACQUER    = Color.web("#512C3A");
  static final public Color AQUATIC_AWE       = Color.web("#5DC7B7");
  static final public Color RETRO_BLUE        = Color.web("#71ADBA");
  static final public Color NEON_FLARE        = Color.web("#FF654F");
  static final public Color RAY_FLOWER        = Color.web("#FFCA5C");
  static final public Color SUNSET_CORAL      = Color.web("#F0686C");
  static final public Color TRANSCENDENT_PINK = Color.web("#E3B8C9");

  // **** Pantone Colors 2025 (https://www.w3schools.com/colors/colors_2025.asp)
  static final public Color MOCHA_MOUSSE  = Color.web("#A47864");

  static final public Color BRAN        = Color.web("#A66E4A");
  static final public Color CROCUS      = Color.web("#C67FAE");
  static final public Color LIME_CREAM  = Color.web("#D7E8BC");
  static final public Color LIMPET_SHELL= Color.web("#98DDDF");
  static final public Color WHITE_GRAPE = Color.web("#A6BE47");
  static final public Color DEJAVU_BLUE = Color.web("#2E5283");
  static final public Color KASHMIR     = Color.web("#6F8D6A");
  static final public Color MISTED_MARIGOLD = Color.web("#E3BD33");
  static final public Color ORANGEADE   = Color.web("#E2552D");
  static final public Color COCOON      = Color.web("#C9B27C");
  static final public Color POPPY_RED   = Color.web("#DC343B");
  static final public Color DAMSON      = Color.web("#854C65");

  // **** Shades
  static final public Color ECLIPSE     = Color.web("#343148");
  static final public Color ANTIQUE_WHITE = Color.web("#EDE3D2");
  static final public Color RUMRASIN    = Color.web("#583432");
  static final public Color MOONBEAM    = Color.web("#CDC6BD");
  static final public Color BLUE_GRANITE= Color.web("#717388");
  static final public Color VAPOR_BLUE  = Color.web("#BDBEBF");
  static final public Color CROWN_BLUE  = Color.web("#464B65");
  static final public Color WINTERBERRY = Color.web("#Be394F");


  static public String toRGBCode( Color color ) {
    return String.format( "#%02X%02X%02X",
      (int)( color.getRed() * 255 ),
      (int)( color.getGreen() * 255 ),
      (int)( color.getBlue() * 255 ) );
  }

  static public Color transparent( Color pC, double pAlpha ) {
    return new Color( pC.getRed(), pC.getGreen(), pC.getBlue(), pAlpha );
  }

  static public Color BACKGROUND = Color.BLACK;
  static public Color DEFAULT_TEXTFOREGROUNDLIGHT = Color.WHITE;
  static public Color DEFAULT_TEXTFOREGROUNDDARK = Color.BLACK;

}
