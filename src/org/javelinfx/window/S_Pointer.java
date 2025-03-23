package org.javelinfx.window;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class S_Pointer {

  public enum POINTER {
    NONE,
    PRIMARY,
    SECONDARY,
    MIDDLE,
    UNKNOWN
  }

  /**
   * convertFrom
   * @param pME
   * @return
   */
  static public POINTER convertFrom( MouseEvent pME ) {
    if (pME.getButton()== MouseButton.PRIMARY) {
      return POINTER.PRIMARY;
    } else if (pME.getButton()==MouseButton.SECONDARY) {
      return POINTER.SECONDARY;
    } else if (pME.getButton()==MouseButton.MIDDLE) {
      return POINTER.MIDDLE;
    } else {
      return POINTER.NONE;
    }
  }


}
