package org.javelinfx.spatial;

import org.javelinfx.units.EUDistance;

public class SP_Position implements ISP_Position {

  static public ISP_Position of( double pX, double pY, EUDistance pXYUnit ) {
    return new SP_Position(pX, pY, 0.0, pXYUnit, EUDistance.UNKNOWN );
  }

  private final double mX;
  private final double mY;
  private final double mZ;
  private final EUDistance mXYUnit;
  private final EUDistance mZUnit;

  private SP_Position(double pX, double pY, double pZ, EUDistance pXYUnit, EUDistance pZUnit) {
    mX = pX;
    mY = pY;
    mZ = pZ;
    mXYUnit = pXYUnit;
    mZUnit = pZUnit;
    return;
  }

  @Override
  public double x() {
    return mX;
  }

  @Override
  public double y() {
    return mY;
  }

  @Override
  public double z() {
    return mZ;
  }

  @Override
  public EUDistance xyUnit() {
    return mXYUnit;
  }

  @Override
  public EUDistance zUnit() {
    return mZUnit;
  }

  @Override
  public ISP_Position x(double pX) {
    if (pX!=mX) {
      return new SP_Position(pX, mY, mZ, mXYUnit, mZUnit);
    }
    return this;
  }

  @Override
  public ISP_Position y(double pY) {
    if (pY!=mY) {
      return new SP_Position(mX, pY, mZ, mXYUnit, mZUnit);
    }
    return this;
  }


  @Override
  public ISP_Position add( double pX, double pY ) {
    if ((pX==0) && (pY==0)) {
      return this;
    }
    return new SP_Position( mX + pX, mY + pY, mZ, mXYUnit, mZUnit );
  }


  @Override
  public String toString() {
    return "SP_Position{" +
      "mX=" + mX +
      ", mY=" + mY +
      ", mZ=" + mZ +
      ", mXYUnit=" + mXYUnit +
      ", mZUnit=" + mZUnit +
      '}';
  }
}
