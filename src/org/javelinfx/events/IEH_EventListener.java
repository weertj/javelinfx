package org.javelinfx.events;

import javafx.event.EventTarget;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import org.javelinfx.spatial.ISP_Position;
import org.javelinfx.window.S_Pointer;

public interface IEH_EventListener {

  enum UIMAP {
    VERTICALLIST_SCROLL,
    HORIZONTALLIST_SCROLL,
  }

  default EH_Select isDragSource() {
    return EH_Select.OFF;
  }

  default void dragDetected(IEventHandler pEventHandler, ISP_Position pStartPosition ) {
    return;
  }

  default Image dragView() {
    return null;
  }

  default void dropDetected( IEventHandler pEventHandler, ISP_Position pStartPosition, EventTarget pTarget  ) {
    return;
  }

  default void moved( IEventHandler pEventHandler, ISP_Position pLastPosition, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pSource, EventTarget pTarget ) {
    return;
  }

  // ***************************************************************************
  // **** PRESSED
  default void pressed( IEventHandler pEventHandler, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pSource, EventTarget pTarget ) {
    return;
  }

  // ***************************************************************************
  // **** RELEASED

  default void released(IEventHandler pEventHandler, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pTarget ) {
    return;
  }

  default void uiMap( IEventHandler pEventHandler, UIMAP pMap, double pValue ) {
    return;
  }

  default boolean mouseWheel( IEventHandler pEventHandler, ScrollEvent pEvent ) {
    return false;
  }


}
