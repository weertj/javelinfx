package org.javelinfx.textfield;

import javafx.scene.control.TextField;

public class STextField {

  static private void onEnteredData(TextField pTF, Runnable pR) {
    pTF.setOnAction( e -> {
      pR.run();
    });
    pTF.setOnKeyReleased( (e) -> {
      pR.run();
    });
    return;
  }

  static public void initTextField(TextField pTF, Runnable pR) {
    onEnteredData(pTF, pR);
    return;
  }


  private STextField() {
    return;
  }

}
