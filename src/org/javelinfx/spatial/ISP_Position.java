package org.javelinfx.spatial;

import org.javelinfx.units.EUDistance;

public interface ISP_Position extends ISP_Area {

  double x();
  double y();
  default double z() {
    return 0.0;
  }

  default EUDistance xyUnit() {
    return EUDistance.UNKNOWN;
  }

  default EUDistance zUnit() {
    return EUDistance.UNKNOWN;
  }

  default ISP_Position x( double pX ) {
    throw new UnsupportedOperationException();
  }

  default ISP_Position y( double pY ) {
    throw new UnsupportedOperationException();
  }

  // **** Mutation methods
  default ISP_Position add( double pX, double pY ) {
    throw new UnsupportedOperationException();
  }


  // **** ISP_Area
  @Override
  default ISP_Position center() {
    return this;
  }
}
