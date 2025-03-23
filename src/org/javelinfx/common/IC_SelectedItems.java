package org.javelinfx.common;

import javafx.collections.ObservableList;
import org.javelinfx.events.EH_Select;

public interface IC_SelectedItems<E> {

  ObservableList<E> selectedItems();

  void applyItem( EH_Select pSelect, E pItem );
  EH_Select forItem(E pItem );

}
