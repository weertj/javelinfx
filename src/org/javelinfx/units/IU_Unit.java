package org.javelinfx.units;

public interface IU_Unit {

  default double base() {
    return 1.0;
  }

  default double convertTo(double pD, IU_Unit pU) {
    if (this == pU) {
      return pD;
    }
    return (pD / base()) * pU.base();
  }


}
