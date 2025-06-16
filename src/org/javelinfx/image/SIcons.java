package org.javelinfx.image;

import javafx.scene.paint.Color;
import org.javelinfx.filesystem.IFS_File;

import java.io.IOException;

public class SIcons {

  static private final IIconTintList ICONDATABASE = IconTintList.of();

  static public void loadAllIconFrom(IFS_File pBaseDirectory,IFS_File pDirectory) {
    for( IFS_File f : pDirectory.children()) {
      if (f.isDirectory()) {
        loadAllIconFrom(pBaseDirectory,f);
      } else {
        String base = pBaseDirectory.uriPath();
        String path = f.uriPath();
        if (f.isFile() && path.endsWith(".png")) {
          String key = path.substring(base.length());
          key = key.substring(0,key.length()-".png".length());
          icon( key, f,null );
        }
      }
    }
    return;
  }

  static public IIcon icon( String pResource, Color pNewBaseColor ) {
    return icon(pResource, null, pNewBaseColor);
  }

  static public IIcon icon( String pResource, IFS_File pFilePath, Color pNewBaseColor ) {
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
        if (pFilePath.exists()) {
          try {
            ICONDATABASE.addImageResource(key, pFilePath, Color.BLACK);
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