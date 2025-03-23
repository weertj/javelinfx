package org.javelinfx.units;

public enum EUAngle implements IU_Unit {

  TURN(1.0,"turn", "%.2f"),
  RADIAN(Math.PI*2,"rad", "%.2f"),
  DEGREE(360.0,"°", "%.2f"),
  GON(400.0,"g", "%.2f"),
  HEADING_TRUE(360.0,"°", "%.2f");

  private final double mB;
  private final String mHuman;
  private final String mFormat;

  EUAngle(double pB, String pHuman, String pFormat) {
    mB = pB;
    mHuman = pHuman;
    mFormat = pFormat;
    return;
  }

  @Override
  public double base() {
    return mB;
  }

  @Override
  public double convertTo(double pD, IU_Unit pU) {
    if (this == pU) {
      return pD;
    }
    if (pU==HEADING_TRUE) { // **** TODO, better conversion for others
      double degrees = (pD / mB) * pU.base();
      degrees = (360-degrees)+90;
      while( degrees>=360 ) {
        degrees -= 360;
      }
      return degrees;
    } else {
      return (pD / mB) * pU.base();
    }
  }
}
