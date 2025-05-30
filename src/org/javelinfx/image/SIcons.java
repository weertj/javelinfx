package org.javelinfx.image;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;

public class SIcons {

  static private final IIconTintList ICONDATABASE = IconTintList.of();

  static public IIcon icon( String pResource, Color pNewBaseColor ) {
    return icon(pResource, null, pNewBaseColor);
  }

  static public IIcon icon( String pResource, String pFilePath, Color pNewBaseColor ) {
    String key = pResource + (pNewBaseColor==null?"" : "_" +pNewBaseColor );
    IIcon icon = ICONDATABASE.iconFor(key );
    if (icon!=null && !icon.isEmpty()) {
      return icon;
    }
    if (icon==null) {
      if (pFilePath==null) {
        ICONDATABASE.convertBaseColor(pResource, key,Color.BLACK, pNewBaseColor);
        icon = ICONDATABASE.iconFor(key);
        return icon;
      } else {
        File f = new File(pFilePath);
        if (f.exists()) {
          try {
            ICONDATABASE.addImageResource(key, f, Color.BLACK);
            if (pNewBaseColor != null) {
              ICONDATABASE.convertBaseColor(pResource,key, Color.BLACK, pNewBaseColor);
            }
            icon = ICONDATABASE.iconFor(key);
            return icon;
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    } else {
      ICONDATABASE.addIcon(GIcon.NULL, key);
    }
    return icon;
  }


  private SIcons() {
    return;
  }

}