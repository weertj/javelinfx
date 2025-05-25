package org.javelinfx.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.jgalaxy.IEntity;
import org.jgalaxy.units.IJG_Group;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class STableView {

  static public <T extends IEntity> void synchItems(TableView<T> pTableView, List<T> pItems) {
    ObservableList<T> items = pTableView.getItems();

    // Save current selection
    T selected = pTableView.getSelectionModel().getSelectedItem();

    // Build a map of current items for fast lookup
    Map<Object, T> currentMap = items.stream()
      .collect(Collectors.toMap(T::id, Function.identity())); // Use unique ID method

    // Build a map of new items for comparison
    Map<Object, T> newMap = pItems.stream()
      .collect(Collectors.toMap(T::id, Function.identity()));

    // Remove groups that are no longer present
    items.removeIf(group -> !newMap.containsKey(group.id()));

    // Add or replace updated/new groups
    for (T newGroup : pItems) {
      T existing = currentMap.get(newGroup.id());
      if (existing == null) {
        // New group
        items.add(newGroup);
      } else if (!newGroup.equals(existing)) {
        // Optional: replace updated item
        int index = items.indexOf(existing);
        items.set(index, newGroup);
      }
      // Else same group, leave untouched
    }

    // Restore selection if still present
    if (selected != null && newMap.containsKey(selected.id())) {
      T updatedSelected = newMap.get(selected.id());
      pTableView.getSelectionModel().select(updatedSelected);
    }
    pTableView.refresh();
    return;
  }


  private STableView() {
    return;
  }

}
