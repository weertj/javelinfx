package org.javelinfx.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;

public class SButtons {

  static public void initButton(
        ButtonBase  pButton,
        EventHandler<ActionEvent> pEvent) {

    if (pEvent!=null) {
      pButton.setOnAction(pEvent);
    }
    return;
  }


  private SButtons() {
    return;
  }

}
