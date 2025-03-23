package org.javelinfx.spatial;

public record SP_Angle( double angle ) implements ISP_Angle {

  static public ISP_Angle of( double pAngle ) {
    return new SP_Angle(pAngle);
  }



}
