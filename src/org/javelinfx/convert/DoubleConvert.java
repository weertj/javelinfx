package org.javelinfx.convert;


public class DoubleConvert {

  static public double convert( Object pS, double pDefault ) {
    if (pS!=null) {
      if (pS instanceof String s) {
        if (!s.isEmpty()) {
          try {
            return Double.parseDouble(s);
          } catch( NumberFormatException ne ) {
            return pDefault;
          }
        }
      } else if (pS instanceof Number n) {
        return n.doubleValue();
      }
    }
    return pDefault;
  }

  private DoubleConvert() {
    return;
  }

}
