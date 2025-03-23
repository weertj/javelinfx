package org.javelinfx.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.javelinfx.events.EH_Select;

public class C_SelectedItems<E> implements IC_SelectedItems<E> {

  static public <E> IC_SelectedItems<E> of() {
    return new C_SelectedItems<>();
  }

  private final ObservableList<E> mSelectedItems = FXCollections.observableArrayList();

  private C_SelectedItems() {
    return;
  }

  @Override
  public ObservableList<E> selectedItems() {
    return mSelectedItems;
  }

  @Override
  public void applyItem(EH_Select pSelect, E pItem) {
    if (pSelect.asBool()) {
      mSelectedItems.add(pItem);
    } else {
      mSelectedItems.remove(pItem);
    }
    return;
  }

  @Override
  public EH_Select forItem(E pItem) {
    return EH_Select.from(mSelectedItems.contains(pItem));
  }
}
