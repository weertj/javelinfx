package org.javelinfx.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.javelinfx.image.SImages;

public class SButtons {

  static public void initButton(
    ButtonBase  pButton,
    EventHandler<ActionEvent> pEvent) {
    initButton(pButton, null, pEvent);
  }

  static public void initButton(
        ButtonBase  pButton,
        String                    pIconImagePath,
        EventHandler<ActionEvent> pEvent) {

    if (pEvent!=null) {
      pButton.setOnAction(pEvent);
    }

    Node child = pButton.getGraphic();
    if (pIconImagePath!=null && (child instanceof ImageView imageView)) {
      Image image = imageView.getImage();
      if (image==null) {
        SImages.setImage( imageView, pIconImagePath );
        image = imageView.getImage();
      }
    }
    return;
  }


  private SButtons() {
    return;
  }

}
