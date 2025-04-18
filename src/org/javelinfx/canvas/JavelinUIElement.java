package org.javelinfx.canvas;

import org.javelinfx.events.EH_Select;
import org.javelinfx.player.IJL_PlayerContext;
import org.javelinfx.spatial.ISP_Area;
import org.javelinfx.spatial.ISP_Position;
import org.javelinfx.window.S_Pointer;

public class JavelinUIElement extends JavelinRenderItem implements IJavelinUIElement {

  private boolean mHover = false;
  private double mGrow = 0.0;

  protected JavelinUIElement(String id, Object element, ISP_Position position) {
    super(id, element, position);
  }

  @Override
  public EH_Select hitsArea(ISP_Area pPos) {
    var outline = getOutline();
    if (outline!=null) {
      return EH_Select.from(outline.contains(pPos.center().x(), pPos.center().y()));
    }
    return EH_Select.ALWAYSOFF;
  }

  @Override
  public void render(IJavelinCanvas pCanvas, IJL_PlayerContext pContext) {
    super.render(pCanvas, pContext);
    if (hovered()) {
      mGrow += 0.05;
      mGrow = Math.min(2.0, mGrow);
    } else {
      mGrow -= 0.03;
      mGrow = Math.max(0.0, mGrow);
    }
    return;
  }

  protected double grow() {
    return mGrow;
  }

  protected boolean hovered() {
    return mHover;
  }

  @Override
  public void pointerEntered(IJavelinCanvas pCanvas, IJL_PlayerContext pContext, S_Pointer.POINTER pPointer, ISP_Position pPosition) {
    mHover = true;
    return;
  }

  @Override
  public void pointerLeft( IJavelinCanvas pCanvas, IJL_PlayerContext pContext, ISP_Position pPosition) {
    mHover = false;
    return;
  }


}
