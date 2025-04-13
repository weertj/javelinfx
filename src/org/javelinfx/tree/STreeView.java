package org.javelinfx.tree;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

public class STreeView {

  static public TreeItem findTreeItemBy( TreeView pTreeView, TreeItem pCurrent, Object pEntity, boolean pResolveChildren ) {
    return findTreeItemBy( pTreeView,pCurrent,pEntity, Objects::equals, pResolveChildren );
  }

  static public TreeItem findTreeItemBy( TreeView pTreeView, TreeItem pCurrent, Object pEntity, BiPredicate<Object,Object> pEquals, boolean pResolveChildren ) {
    if (pCurrent==null) pCurrent = pTreeView.getRoot();
    if (pEquals.test(pCurrent.getValue(),pEntity)) {
      return pCurrent;
    }
    List<TreeItem> listtis = pCurrent.getChildren();
    for (TreeItem child : new ArrayList<>(listtis ) ) {
      TreeItem result = findTreeItemBy( pTreeView,child, pEntity, pEquals, pResolveChildren);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  private STreeView() {
    return;
  }

}
