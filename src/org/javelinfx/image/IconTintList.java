package org.javelinfx.image;

import javafx.scene.paint.Color;
import org.javelinfx.colors.SColors;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IconTintList implements IIconTintList {

  static public IIconTintList of() {
    return new IconTintList();
  }

  private Map<String, IIcon> mBaseIconSet = HashMap.newHashMap(32);

  private IconTintList() {
    return;
  }


  @Override
  public IIcon iconFor(String pResource) {
    return mBaseIconSet.get(pResource);
  }

  @Override
  public void addImageResource(String pId, File pResource, Color pBaseColor) throws IOException {
    IIcon icon = GIcon.of(pResource,pBaseColor);
    mBaseIconSet.put( pId, icon );
    return;
  }

  @Override
  public void addIcon(IIcon pIcon, String pResource) {
    if (!mBaseIconSet.containsKey(pResource)) {
      mBaseIconSet.put(pResource, pIcon);
    }
    return;
  }

  @Override
  public void convertBaseColor(String pBaseId, String pNewKey, Color pBaseColor, Color pNewBaseColor) {
    IIcon icon = mBaseIconSet.get( pBaseId );
    if (icon!=null && icon.baseColor().equals(pBaseColor)) {
      BufferedImage bufferedImage = GImage.imageTintColor( icon.getOrCreateBufferedImage(), SColors.toAWTColor(pBaseColor ), SColors.toAWTColor(pNewBaseColor) );
      mBaseIconSet.put( pNewKey, GIcon.of( GIcon.toImage(bufferedImage), pNewBaseColor ) );
      bufferedImage.flush();
    }
    return;
  }

}
