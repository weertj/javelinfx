package org.javelinfx.engine;

import javafx.application.Application;
import javafx.stage.Stage;
import org.javelinfx.filesystem.FS_File;
import org.javelinfx.filesystem.IFS_File;
import org.javelinfx.fonts.F_Fonts;
import org.javelinfx.system.JavelinSystem;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class startJavelin extends Application {

  static public Parameters PARAMETERS;

  static public F_Fonts FONTS = F_Fonts.getDefault();

  private IJavelinFX mEngine;

  public startJavelin() {
    return;
  }

  @Override
  public void start( Stage pPrimaryStage) throws Exception {
    PARAMETERS = getParameters();
    Properties prop = new Properties();
    JavelinSystem.determineWorkingDirectory();
    File jlfxprops = new File("javelinfx.properties");
    try( FileInputStream fis = new FileInputStream(jlfxprops)) {
      prop.load( fis );
    }
    JavelinSystem.prepareForSystem(prop.getProperty("javelinfx.name", "<no name>"));
    IFS_File fontsprops = FS_File.of("data/fonts-" + JavelinSystem.OSSYS() + ".properties");
    if (fontsprops.exists()) {
      Properties fontprop = new Properties();
      fontprop.load(fontsprops.inputstream());
      FONTS = FONTS.loadFontsFromDirectory(JavelinSystem.currentWorkingDirectory().child("data"), fontprop, 1);
    }

    String engineclass = prop.getProperty( "javelinfx.engine", "org.javelinfx.engine.JavelinFX" );
    mEngine = (IJavelinFX)Class.forName( engineclass ).getDeclaredConstructor().newInstance();
    mEngine.setPrimaryStage( pPrimaryStage );
    mEngine.init(JavelinSystem.currentWorkingDirectory(), prop);
    mEngine.start();
    return;
  }

  @Override
  public void stop() throws Exception {
    mEngine.stop();
    mEngine.destroy();
    return;
  }
}
