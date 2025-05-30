package org.javelinfx.player;

import org.javelinfx.canvas.IJavelinRenderItem;
import org.javelinfx.canvas.IJavelinUIElement;
import org.javelinfx.common.IC_SelectedItems;
import org.javelinfx.spatial.ISP_Area;
import org.jgalaxy.engine.IJG_Faction;
import org.jgalaxy.units.IJG_Unit;

import java.util.List;
import java.util.Optional;

public interface IJL_PlayerContext {

  void clearRenderItems();

  void cleanUpRenderItems(IJG_Faction pFaction);

  List<IJavelinRenderItem> renderItems( int pLevel );

  void addRenderItem( int pLevel,IJavelinRenderItem pItem );
  void removeRenderItem( int pLevel,IJavelinRenderItem pItem );

  Optional<IJavelinRenderItem> getRenderItem(int pLevel, IJG_Unit pUnit );

  List<IJavelinUIElement> retrieveByArea(ISP_Area pArea );

  IC_SelectedItems<Object> selectedEntities();
  IC_SelectedItems<IJavelinUIElement> selectedItems();

}
