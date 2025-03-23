package org.javelinfx.log;

import java.util.logging.Level;

public class LOG_Handler {

  static public void log(String pSource, Level pLevel, Object pMessage, Throwable pThrowable ) {
    if (pLevel.intValue()>=Level.FINE.intValue()) {
      System.out.println(pSource + ": " + pLevel + ": " + pMessage + ": " + pThrowable);
    }
    if (pThrowable != null) {
      pThrowable.printStackTrace();
    }
    return;
  }

  private LOG_Handler() {
    return;
  }

}
