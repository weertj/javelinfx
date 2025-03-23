package org.javelinfx.spatial;

public interface ISP_Vector extends ISP_Position {

  double    magnitude();
  ISP_Angle angle();

  ISP_Position target();

}
