package org.javelinfx.tree;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public interface ITreeEntity {

  ObservableList<TreeItem<ITreeEntity>> childrenAsIs();

}
