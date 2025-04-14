package org.javelinfx.player;

import org.javelinfx.canvas.IJavelinRenderItem;
import org.javelinfx.canvas.IJavelinUIElement;
import org.javelinfx.common.IC_SelectedItems;
import org.javelinfx.spatial.ISP_Area;

import java.util.List;

public interface IJL_PlayerContext {

  void clearRenderItems();

  List<IJavelinRenderItem> renderItems( int pLevel );

  void addRenderItem( int pLevel,IJavelinRenderItem pItem );

  List<IJavelinUIElement> retrieveByArea(ISP_Area pArea );

  IC_SelectedItems<IJavelinUIElement> selectedItems();

}
