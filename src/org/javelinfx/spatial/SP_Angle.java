package org.javelinfx.spatial;

public record SP_Angle( double angle ) implements ISP_Angle {

  static public ISP_Angle of( double pAngle ) {
    return new SP_Angle(pAngle);
  }


  @Override
  public ISP_Angle add(double pA) {
    if (pA!=0.0) {
      return of((angle+pA)%(2*Math.PI));
    }
    return this;
  }


}
