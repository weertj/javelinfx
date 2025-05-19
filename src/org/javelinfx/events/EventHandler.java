package org.javelinfx.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.stage.Window;
import org.javelinfx.common.C_Taglist;
import org.javelinfx.common.IC_Taglist;
import org.javelinfx.log.LOG_Handler;
import org.javelinfx.spatial.ISP_Position;
import org.javelinfx.spatial.SP_Position;
import org.javelinfx.units.EUDistance;
import org.javelinfx.window.S_Pane;
import org.javelinfx.window.S_Pointer;
import org.javelinfx.window.S_Window;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class EventHandler implements IEventHandler {

  static private final IC_Taglist<Window, IEventHandler> DEFAULT = C_Taglist.ofMutable();

  // **** For the custom dragboard
  static public final String DRAGSOURCE     = "dragSource";
  static public final String DRAGTARGET     = "dragTarget";
  static public final String ENTERED        = "entered";
  static public final String STARTPOSITION  = "startPosition";

  static public IEventHandler of( Node pNode ) {
    return of( S_Window.windowFromPane( S_Pane.getTopParentPane(pNode )));
  }

  static public synchronized IEventHandler of(Window pWindow) {
    if (!DEFAULT.contains(pWindow)) {
      DEFAULT.put(pWindow, new EventHandler(pWindow));
    }
    return DEFAULT.value(pWindow);
  }

  private final Window      mWindow;
  private final List<Node>  mNodes = new ArrayList<>(16);
  private final IC_Taglist<S_Pointer.POINTER, ISP_Position> mLastPositions = C_Taglist.ofMutable();
  private final IC_Taglist<EventTarget,IEH_EventListener>   mCallbacks = C_Taglist.ofMutable();
  private       IC_Taglist<String, Object>                  mCustomDragBoard = C_Taglist.of();

  private       long      mLastScrollMessage = System.currentTimeMillis();
  private       EH_Select mScrollStarted  = EH_Select.OFF;
  private       EH_Select mScrollTouched  = EH_Select.OFF;
  private       EH_Select mDragging       = EH_Select.OFF;
  private       double    mTouchScrollXModifier     = -1.0;
  private       double    mTouchScrollYModifier     = -1.0;
  private       double    mMouseWheelScrollModifier = 1.0;


  private final javafx.event.EventHandler<Event> mEvents = e -> {
    try {
      handleEvent(e);
    } catch (Throwable err) {
      LOG_Handler.log("org.javelinfx.events.EventHandler", Level.SEVERE, err.getMessage(), err);
    }
  };

  private EventHandler(Window pWindow) {
    mWindow = pWindow;
    mWindow.addEventHandler(EventType.ROOT, mEvents);
    return;
  }

  @Override
  public void addNode(Node pNode) {
    if (!mNodes.contains(pNode)) {
      mNodes.add(pNode);
      pNode.addEventHandler(EventType.ROOT, mEvents );
    }
    return;
  }

  @Override
  public void removeNode(Node pNode) {
    pNode.removeEventHandler( EventType.ROOT, mEvents );
    synchronized(mCallbacks) {
      mCallbacks.remove( pNode );
    }
    mNodes.remove(pNode);
    return;
  }

  @Override
  public void addEventCallback( EventTarget pNode, IEH_EventListener pListener) {
    if (pNode instanceof Node node) {
      node.setMouseTransparent(false);
      addNode(node);
    }
    synchronized(mCallbacks) {
      if (!mCallbacks.contains(pNode,pListener)) {
        mCallbacks.put(pNode, pListener);
      }
    }
    return;
  }

  @Override
  public void removeEventCallback(EventTarget pNode, IEH_EventListener pListener) {
    synchronized(mCallbacks) {
      if (pListener==null) {
        mCallbacks.remove( pNode );
      } else {
        mCallbacks.removeValue( pNode, pListener );
      }
      if (mCallbacks.values(pNode).isEmpty() && pNode instanceof Node node) {
        removeNode(node);
      }
    }
    return;
  }


  private void handleEvent(Event pEvent) {
    EventType<?> eventType = pEvent.getEventType();
    LOG_Handler.log( "org.javelinfx.events.EventHandler.handleEvent",Level.FINEST, pEvent, null );
    if (pEvent.getSource() instanceof EventTarget snode) {
      synchronized (mCallbacks) {

        if (mCallbacks.contains(snode)) {

          // ******************************************************
          // **** DRAG_DETECTED
          if (eventType == MouseEvent.DRAG_DETECTED && (snode instanceof Node node)) {
            mCustomDragBoard = C_Taglist.of();
            MouseEvent mevent = (MouseEvent) pEvent;
            ClipboardContent clipboardContent = null;
            for (IEH_EventListener el : mCallbacks.values(snode)) {
              if (el.isDragSource().asBool()) {
                if (clipboardContent == null) {
                  clipboardContent = new ClipboardContent();
                }
                mDragging = EH_Select.ON;
                node.startFullDrag();
                Dragboard dragboard = node.startDragAndDrop(TransferMode.ANY);
                el.dragDetected(this, SP_Position.of(mevent.getX(), mevent.getY(), EUDistance.PIXELS));
                dragboard.setDragView(el.dragView());
                mCustomDragBoard = mCustomDragBoard.put(DRAGSOURCE, snode)
                  .put(STARTPOSITION, SP_Position.of(mevent.getX(), mevent.getY(), EUDistance.PIXELS));
                dragboard.setContent(clipboardContent);
              }
            }
            pEvent.consume();
          }


          // *********************************************************************
          // **** MOUSE_RELEASED || DRAG_DROPPED
          if (eventType == MouseEvent.MOUSE_RELEASED || eventType == DragEvent.DRAG_DROPPED || eventType == DragEvent.DRAG_DONE) {
            S_Pointer.POINTER pointer = S_Pointer.POINTER.UNKNOWN;
            if (pEvent instanceof DragEvent devent) {
              for (IEH_EventListener el : mCallbacks.values(snode)) {
                el.dropDetected(this, SP_Position.of(devent.getX(), devent.getY(), EUDistance.PIXELS), snode);
              }
//            devent.setDropCompleted( true );
              mDragging = EH_Select.OFF;
              releasedEvent(snode, SP_Position.of(devent.getX(), devent.getY(), EUDistance.PIXELS), pointer, pEvent.getTarget());
            } else {
              MouseEvent mevent = (MouseEvent) pEvent;
              pointer = S_Pointer.convertFrom(mevent);
              if (mDragging.asBool() && pointer == S_Pointer.POINTER.PRIMARY) {
                for (IEH_EventListener el : mCallbacks.values(snode)) {
                  el.dropDetected(this, SP_Position.of(mevent.getX(), mevent.getY(), EUDistance.PIXELS), snode);
                }
                mDragging = EH_Select.OFF;
              }
              releasedEvent(snode, SP_Position.of(mevent.getX(), mevent.getY(), EUDistance.PIXELS), pointer, pEvent.getTarget());
            }
          }


          // **** No dragging allowed
          if (mDragging.asBool()) {
//          pEvent.consume();
          } else {

            // **** MOUSE_MOVED
            if (eventType==MouseEvent.MOUSE_MOVED || eventType==MouseEvent.MOUSE_DRAGGED) {
              MouseEvent mevent = (MouseEvent)pEvent;
              S_Pointer.POINTER pointer = S_Pointer.convertFrom(mevent);
              var pos = SP_Position.of(mevent.getX(),mevent.getY(),EUDistance.PIXELS);
              movedEvent( snode, mLastPositions.value(pointer), pos, pointer, mevent.getTarget() );
              mLastPositions.remove(pointer );
              mLastPositions.put(pointer, pos);
//            pEvent.consume();
            }

            // **** MOUSE_CLICKED
            if (eventType==MouseEvent.MOUSE_CLICKED) {
              MouseEvent mevent = (MouseEvent)pEvent;
              S_Pointer.POINTER pointer = S_Pointer.convertFrom(mevent);
              var pos = SP_Position.of(mevent.getX(),mevent.getY(),EUDistance.PIXELS);
              clickedEvent( snode, pos, pointer, mevent.getTarget(), mevent.getClickCount() );
              mLastPositions.remove(pointer );
              mLastPositions.put(pointer, pos);
            }

            // **** MOUSE_PRESSED
            if (eventType==MouseEvent.MOUSE_PRESSED) {
              MouseEvent mevent = (MouseEvent)pEvent;
              S_Pointer.POINTER pointer = S_Pointer.convertFrom(mevent);
              var pos = SP_Position.of(mevent.getX(),mevent.getY(),EUDistance.PIXELS);
              pressedEvent( snode, pos, pointer, mevent.getTarget() );
              mLastPositions.remove(pointer );
              mLastPositions.put(pointer, pos);
//            pEvent.consume();
            }

            // **** SCROLL_STARTED
            if (eventType==ScrollEvent.SCROLL_STARTED) {
              mScrollStarted = EH_Select.ON;
              mScrollTouched = EH_Select.ON;
            }

            // **** SCROLL
            if (eventType==ScrollEvent.SCROLL) {
              scrollEvent( snode, (ScrollEvent)pEvent );
            }

            // **** SCROLL_FINISHED
            if (eventType==ScrollEvent.SCROLL_FINISHED) {
              mScrollStarted = EH_Select.OFF;
            }


          }

        }
      }
    }
  }


  private void clickedEvent( EventTarget pNode, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pTarget, int pClicks ) {
    for( IEH_EventListener el : mCallbacks.values(pNode)) {
//      if (el.beforeClickedCheck(pTarget)) {
//        new Thread( () -> {
//          GEN_System.sleep(el.clickedDelay());
//          FX_Platform.runLater( () -> {
//            try {
//              if (pClicks>1) {
//                el.doubleClicked(this, pPosition, pPointer, pNode, pTarget);
//              } else {
//                el.clicked(this, pPosition, pPointer, pNode, pTarget);
//              }
//            } finally {
//              el.afterClicked();
//            }
//          });
//        }).start();
//      }
    }
    return;
  }

  private void pressedEvent( EventTarget pNode, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pTarget ) {
    synchronized(mCallbacks) {
      for( IEH_EventListener el : mCallbacks.values(pNode)) {
        el.pressed(this, pPosition, pPointer, pNode, pTarget);
      }
    }
    return;
  }


  private void releasedEvent( EventTarget pNode, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pTarget ) {
    synchronized(mCallbacks) {
      for( IEH_EventListener el : mCallbacks.values(pNode)) {
        el.released( this, pPosition, pPointer, pTarget );
      }
    }
    return;
  }

  private void movedEvent( EventTarget pNode, ISP_Position pLastPosition, ISP_Position pPosition, S_Pointer.POINTER pPointer, EventTarget pTarget ) {
    synchronized(mCallbacks) {
      for( IEH_EventListener el : mCallbacks.values(pNode)) {
        el.moved( this, pLastPosition, pPosition, pPointer, pNode, pTarget );
      }
    }
    return;
  }

  private void scrollEvent( EventTarget pNode, ScrollEvent pEvent ) {

    // **** Reset scroll events after 1000ms
    long diff = System.currentTimeMillis() - mLastScrollMessage;
    mLastScrollMessage = System.currentTimeMillis();
    if (!mScrollStarted.asBool() && diff>1000) {
      mScrollTouched = EH_Select.OFF;
    }

    synchronized(mCallbacks) {
      for( IEH_EventListener el : mCallbacks.values(pNode)) {
        if (mScrollTouched.asBool()) {
          if (pEvent.getDeltaY()!=0) {
            el.uiMap( this, IEH_EventListener.UIMAP.VERTICALLIST_SCROLL, pEvent.getDeltaY() * mTouchScrollYModifier );
          }
          if (pEvent.getDeltaX()!=0) {
            el.uiMap( this, IEH_EventListener.UIMAP.HORIZONTALLIST_SCROLL, pEvent.getDeltaX() * mTouchScrollXModifier );
          }
        } else {
          if (pEvent.getDeltaY()!=0) {
            // **** MOUSE WHEEL SCROLL
            if (!el.mouseWheel( this, pEvent )) {
              el.uiMap(this, IEH_EventListener.UIMAP.VERTICALLIST_SCROLL, pEvent.getDeltaY() * mMouseWheelScrollModifier);
            }
          }
        }
      }
    }
    return;
  }

}
