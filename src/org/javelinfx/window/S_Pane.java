package org.javelinfx.window;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class S_Pane {

  static public Pane getTopParentPane(final Node pN ) {
    Parent parent = pN.getParent();
    if (parent==null) {
      return (Pane)pN;
    }
    while( parent!=null ) {
      Parent parent2 = parent.getParent();
      if (parent2==null) {
        return (Pane)parent;
      }
      parent = parent2;
    }
    return null;
  }

  static public void setAnchors( Node pNode, Double pLeft, Double pRight, Double pTop, Double pBottom ) {
    if (pLeft!=null) {
      AnchorPane.setLeftAnchor(pNode, pLeft );
    }
    if (pRight!=null) {
      AnchorPane.setRightAnchor(pNode, pRight );
    }
    if (pTop!=null) {
      AnchorPane.setTopAnchor(pNode, pTop );
    }
    if (pBottom!=null) {
      AnchorPane.setBottomAnchor(pNode, pBottom );
    }
    return;
  }


}
