package org.javelinfx.units;

public enum EUDistance implements IU_Unit {

  PIXELS_X(0.1,"x", "%.0f", true ),
  PIXELS_Y(0.1,"y", "%.0f", true),
  PIXELS(0.1,"px", "%.0f", true),
  PIXELS_72DPI(2834.64576,"px", "%.0f", true),
  PIXELS_300DPI(11811.024,"px", "%.0f", true),
  METER(1,"m", "%.0f",  false ),
  FEET(3.28083989501,"ft", "%.0f", false),
  KM(0.001,"km", "%.2f", false),
  NM(0.000539956803,"NM", "%.2f", false),
  MILE(0.000621371192,"mi", "%.2f", false),
  LY( 1.057000834024615e-16,"ly", "%.3f", false),
  LS( 3.33564095e-9,"ls", "%.3f", false),
  AU(0.000000000006685,"au", "%.3f", false),
  LONLAT(1.0,"ll", "%.6f", false),
  EPSG3857(1.0,"m", "%.2f", false),
  UTM(1.0,"m", "%.2f", false),
  GRID(1.0,"grid", "%.0f", false),
  RATIO(1.0,"%", "%.2f", false),
  INCH(39.37007873,"in", "%.2f", false),
  MM(1000.0,"mm", "%.0f", false),
  CM(100,"cm", "%.0f",  false ),
  UNKNOWN( 1.0, "", "%d", true );

  // **** Pixels to Unit multiplier
  static public double MAP2PIXELS = 10;

  private final double        mB;
  private final String        mHuman;
  private final String        mFormat;
  private final boolean       mSynthetic;

  EUDistance(double pB, String pHuman, String pFormat, boolean pSynthetic ) {
    mB          = pB;
    mHuman      = pHuman;
    mFormat     = pFormat;
    mSynthetic  = pSynthetic;
    return;
  }


  @Override
  public double base() {
    return mB;
  }




}
