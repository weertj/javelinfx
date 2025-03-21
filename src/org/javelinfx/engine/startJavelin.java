package org.javelinfx.engine;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class startJavelin extends Application {

  private IJavelinFX mEngine;

  public startJavelin() {
    return;
  }

  @Override
  public void start( Stage pPrimaryStage) throws Exception {
    Properties prop = new Properties();
    File jlfxprops = new File("javelinfx.properties");
    try( FileInputStream fis = new FileInputStream(jlfxprops)) {
      prop.load( fis );
    }
    String engineclass = prop.getProperty( "javelinfx.engine", "org.javelinfx.engine.JavelinFX" );
    mEngine = (IJavelinFX)Class.forName( engineclass ).getDeclaredConstructor().newInstance();
    mEngine.setPrimaryStage( pPrimaryStage );
    mEngine.init( prop );
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
