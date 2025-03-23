package org.javelinfx.events;

import javafx.event.EventTarget;
import javafx.scene.Node;

public interface IEventHandler {

  void addNode(     Node pNode );
  void removeNode(  Node pNode );

  void addEventCallback(EventTarget pNode, IEH_EventListener pListener);
  void removeEventCallback( EventTarget pNode, IEH_EventListener pListener);

}
