package org.javelinfx.canvas;

import org.javelinfx.events.EH_Select;
import org.javelinfx.spatial.ISP_Area;
import org.javelinfx.spatial.ISP_Position;

public class JavelinUIElement extends JavelinRenderItem implements IJavelinUIElement {

  protected JavelinUIElement(String id, Object element, ISP_Position position) {
    super(id, element, position);
  }

  @Override
  public EH_Select hitsArea(ISP_Area pPos) {
    return EH_Select.from(getOutline().contains( pPos.center().x(), pPos.center().y() ));
  }
}
