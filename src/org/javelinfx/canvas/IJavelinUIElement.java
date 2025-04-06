package org.javelinfx.canvas;

import org.javelinfx.events.EH_Select;
import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.ISP_Area;
import org.javelinfx.spatial.ISP_Position;
import org.javelinfx.window.S_Pointer;

public interface IJavelinUIElement extends IJavelinRenderItem {

  default EH_Select hitsArea( ISP_Area pPos ) {
    return EH_Select.ALWAYSOFF;
  }

  default void pointerPressed( IJavelinCanvas pCanvas, IJL_PlayerContext pContext, S_Pointer.POINTER pPointer, ISP_Position pPosition ) {
    return;
  }
  default void pointerReleased( IJavelinCanvas pCanvas, IJL_PlayerContext pContext, S_Pointer.POINTER pPointer, ISP_Position pPosition ) {
    return;
  }
  default void pointerEntered( IJavelinCanvas pCanvas, IJL_PlayerContext pContext, S_Pointer.POINTER pPointer, ISP_Position pPosition ) {
    return;
  }
  default void pointerLeft( IJavelinCanvas pCanvas, IJL_PlayerContext pContext, ISP_Position pPosition ) {
    return;
  }
  default void pointerMoved( IJavelinCanvas pCanvas, IJL_PlayerContext pContext, S_Pointer.POINTER pPointer, ISP_Position pPosition ) {
    return;
  }


}
