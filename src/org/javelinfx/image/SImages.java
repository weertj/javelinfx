package org.javelinfx.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.javelinfx.filesystem.IFS_File;
import org.javelinfx.system.JavelinSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

  static public void setImage(ImageView pIV, String pImagePath ) {
    if (pImagePath.isEmpty()) {
      setImage( pIV, (Image)null );
    } else {
      try {
        try (FileInputStream fis = new FileInputStream(pImagePath)) {
          setImage( pIV, new Image(fis) );
        }
      } catch(IOException fe) {
        // **** Try load as resource
        try( InputStream is = SImages.class.getResourceAsStream(pImagePath)) {
          setImage( pIV, new Image(is) );
        } catch(Throwable fe2) {
          setImage( pIV, (Image)null );
        }
      }
    }
    return;
  }

  static public void setImage( ImageView pIV, Image pImage ) {
    if (pImage==null) {
      pIV.setImage( null );
      pIV.setVisible(false);
    } else {
      pIV.setImage( pImage );
      pIV.setVisible(true);
    }
    return;
  }

  private SImages() {
    return;
  }

}
