package org.javelinfx.image;

import javafx.scene.image.Image;
import org.javelinfx.filesystem.IFS_File;
import org.javelinfx.system.JavelinSystem;

import java.util.HashMap;
import java.util.Map;

public class SImages {

  static private IFS_File WORKDIRECTORY = JavelinSystem.currentWorkingDirectory();

  static private Map<String,Image> IMAGES = new HashMap<>(128);

  static public void setWorkdirectory(IFS_File pWorkDirectory) {
    WORKDIRECTORY = pWorkDirectory;
    return;
  }

  static public Image getImage( String pImageId ) {
    Image image = IMAGES.get(pImageId);
    if (image==null) {
      IFS_File file = WORKDIRECTORY.child(pImageId);
      if (file.exists()) {
        image = new Image(file.file().toURI().toString());
        IMAGES.put(pImageId, image);
      }
    }
    return image;
  }

  private SImages() {
    return;
  }

}
