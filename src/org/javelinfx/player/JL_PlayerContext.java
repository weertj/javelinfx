package org.javelinfx.player;

import org.javelinfx.canvas.IJavelinRenderItem;
import org.javelinfx.canvas.IJavelinUIElement;
import org.javelinfx.common.C_SelectedItems;
import org.javelinfx.common.IC_SelectedItems;
import org.javelinfx.spatial.ISP_Area;

import java.util.ArrayList;
import java.util.List;

public class JL_PlayerContext implements IJL_PlayerContext {

  static public IJL_PlayerContext of() {
    return new JL_PlayerContext();
  }

  private final IC_SelectedItems<IJavelinUIElement> mSelectedItems = C_SelectedItems.of();

  private final List<IJavelinRenderItem> mRenderItems0 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems1 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems2 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems3 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems4 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems5 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems6 = new ArrayList<>(256);
  private final List<IJavelinRenderItem> mRenderItems7 = new ArrayList<>(256);

  protected JL_PlayerContext() {
    return;
  }

  @Override
  public IC_SelectedItems<IJavelinUIElement> selectedItems() {
    return mSelectedItems;
  }

  @Override
  public void addRenderItem(int pLevel, IJavelinRenderItem pItem) {
    synchronized(mRenderItems0) {
      switch(pLevel) {
        case 0: mRenderItems0.add(pItem); break;
        case 1: mRenderItems1.add(pItem); break;
        case 2: mRenderItems2.add(pItem); break;
        case 3: mRenderItems3.add(pItem); break;
        case 4: mRenderItems4.add(pItem); break;
        case 5: mRenderItems5.add(pItem); break;
        case 6: mRenderItems6.add(pItem); break;
        case 7: mRenderItems7.add(pItem); break;
      }
    }
    return;
  }

  @Override
  public void clearRenderItems() {
    synchronized (mRenderItems0) {
      mRenderItems0.clear();
      mRenderItems1.clear();
      mRenderItems2.clear();
      mRenderItems3.clear();
      mRenderItems4.clear();
      mRenderItems5.clear();
      mRenderItems6.clear();
      mRenderItems7.clear();
    }
    return;
  }

  @Override
  public List<IJavelinRenderItem> renderItems(int pLevel) {
    switch(pLevel) {
      case 0: return mRenderItems0;
      case 1: return mRenderItems1;
      case 2: return mRenderItems2;
      case 3: return mRenderItems3;
      case 4: return mRenderItems4;
      case 5: return mRenderItems5;
      case 6: return mRenderItems6;
      case 7: return mRenderItems7;
    }
    return List.of();
  }

  @Override
  public List<IJavelinUIElement> retrieveByArea(ISP_Area pArea) {
    List<IJavelinUIElement> hits = new ArrayList<>(8);
    synchronized(mRenderItems0) {
      for (var item : mRenderItems1 ) {
        if (item instanceof IJavelinUIElement uiItem) {
          if (uiItem.hitsArea(pArea).asBool()) {
            hits.add(uiItem);
          }
        }
      }
      for (var item : mRenderItems2 ) {
        if (item instanceof IJavelinUIElement uiItem) {
          if (uiItem.hitsArea(pArea).asBool()) {
            hits.add(uiItem);
          }
        }
      }
      for (var item : mRenderItems3 ) {
        if (item instanceof IJavelinUIElement uiItem) {
          if (uiItem.hitsArea(pArea).asBool()) {
            hits.add(uiItem);
          }
        }
      }
      for (var item : mRenderItems4 ) {
        if (item instanceof IJavelinUIElement uiItem) {
          if (uiItem.hitsArea(pArea).asBool()) {
            hits.add(uiItem);
          }
        }
      }
      for (var item : mRenderItems5 ) {
        if (item instanceof IJavelinUIElement uiItem) {
          if (uiItem.hitsArea(pArea).asBool()) {
            hits.add(uiItem);
          }
        }
      }
    }
    return hits;
  }
}
