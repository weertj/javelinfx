package org.javelinfx.spatial;

public interface ISP_Area {

  default ISP_Position min() {
    return center();
  }
  ISP_Position center();
  default ISP_Position max() {
    return center();
  }


}
