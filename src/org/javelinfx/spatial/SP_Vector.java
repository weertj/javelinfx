package org.javelinfx.spatial;

import org.javelinfx.math.JL_Math;
import org.jgalaxy.gui.Global;

public record SP_Vector(ISP_Angle angle, double magnitude, ISP_Position position ) implements ISP_Vector {

  static public ISP_Vector of(ISP_Angle angle, double magnitude, ISP_Position position) {
    return new SP_Vector(angle, magnitude, position);
  }

  static public ISP_Vector of( ISP_Position pPos1, ISP_Position pPos2 ) {
    return new SP_Vector(
      SP_Angle.of(Math.atan2( pPos2.y()-pPos1.y(), pPos2.x()-pPos1.x())),
      JL_Math.distance( pPos1, pPos2 ),
      pPos1);
  }


  @Override
  public double x() {
    return position.x();
  }

  @Override
  public double y() {
    return position.y();
  }

  @Override
  public ISP_Position target() {
    return
       x( position.x()+magnitude*Math.cos(angle.angle()))
      .y( position.y()+magnitude*Math.sin(angle.angle()) );
  }

  @Override
  public ISP_Vector translate() {
    double cos = Math.cos(angle.angle());
    cos = cos * magnitude;
    return of( angle, magnitude, SP_Position.of(position.x()+cos, position().y()+magnitude*Math.sin(angle.angle()),position.xyUnit()));
  }

  @Override
  public ISP_Vector rotate( ISP_Angle pAngle ) {
    return of( angle.add(pAngle.angle()), magnitude, position );
  }

  @Override
  public ISP_Vector mul(double pScalar) {
    return of( angle,magnitude*pScalar, position );
  }
}
