package org.javelinfx.spatial;

public record SP_Vector( ISP_Angle angle, double magnitude, ISP_Position position ) implements ISP_Vector {

  static public ISP_Vector of(ISP_Angle angle, double magnitude, ISP_Position position) {
    return new SP_Vector(angle, magnitude, position);
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
}
