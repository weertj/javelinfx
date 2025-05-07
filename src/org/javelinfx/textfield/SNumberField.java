package org.javelinfx.textfield;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class SNumberField {


  static private UnaryOperator<TextFormatter.Change> INTEGERFILTER = change -> {
    String newText = change.getControlNewText();
    if (newText.matches("\\d*")) {
      return change;
    }
    return null;
  };

  static private UnaryOperator<TextFormatter.Change> DOUBLEFILTER = change -> {
    String newText = change.getControlNewText();
    if (newText.matches("\\d*(\\.\\d*)?")) {
      return change;
    }
    return null;
  };


  static public TextField makeTextfieldPositiveDoubleOnly(TextField pTF) {
    pTF.setTextFormatter(new TextFormatter<>(DOUBLEFILTER));
    return pTF;
  }

  static public TextField makeTextfieldPositiveIntegerOnly(TextField pTF) {
    pTF.setTextFormatter(new TextFormatter<>(INTEGERFILTER));
    return pTF;
  }

}
