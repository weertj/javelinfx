package org.javelinfx.spatial;

import org.javelinfx.units.EUAngle;

public interface ISP_Angle {

  double angle();
  default EUAngle unit() {
    return EUAngle.RADIAN;
  }

  ISP_Angle add( double pA );

}
