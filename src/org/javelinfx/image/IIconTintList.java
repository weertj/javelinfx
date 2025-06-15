package org.javelinfx.image;

import javafx.scene.paint.Color;
import org.javelinfx.filesystem.IFS_File;

import java.io.File;
import java.io.IOException;

public interface IIconTintList {

  IIcon iconFor( String pResource );

  void addImageResource(String pId, IFS_File pResource, Color pBaseColor ) throws IOException;

  void convertBaseColor( String pBaseId, String pNewKey, Color pBaseColor, Color pNewBaseColor);

  void addIcon( IIcon pIcon, String pResource );

}
