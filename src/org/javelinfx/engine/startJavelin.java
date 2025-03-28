package org.javelinfx.engine;

import javafx.application.Application;
import javafx.stage.Stage;
import org.javelinfx.system.JavelinSystem;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class startJavelin extends Application {

  static public Parameters PARAMETERS;

  private IJavelinFX mEngine;

  public startJavelin() {
    return;
  }

  @Override
  public void start( Stage pPrimaryStage) throws Exception {
    PARAMETERS = getParameters();
    Properties prop = new Properties();
    File jlfxprops = new File("javelinfx.properties");
    try( FileInputStream fis = new FileInputStream(jlfxprops)) {
      prop.load( fis );
    }
    String engineclass = prop.getProperty( "javelinfx.engine", "org.javelinfx.engine.JavelinFX" );
    mEngine = (IJavelinFX)Class.forName( engineclass ).getDeclaredConstructor().newInstance();
    JavelinSystem.prepareForSystem(prop.getProperty("javelinfx.name", "<no name>"));
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
