package org.javelinfx.engine;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.javelinfx.filesystem.IFS_File;
import org.javelinfx.system.JavelinSystem;

import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class JavelinFX implements IJavelinFX, SystemEventListener {

  private Properties      mInitProps;
  private Stage           mPrimaryStage;
  private Scene           mScene;
  private IJMainInterface mMainInterface;

  public JavelinFX() {
    Desktop.getDesktop().addAppEventListener( this );
  }

  @Override
  public void init(IFS_File pWorkDirectory, Properties pProps) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    mInitProps = pProps;
    String miclass = mInitProps.getProperty( "javelinfx.maininterface", "org.javelinfx.engine.JMainInterface" );
    mMainInterface = (IJMainInterface)Class.forName( miclass ).getDeclaredConstructor().newInstance();
    mMainInterface.setEngine( this );
    mMainInterface.init();
    return;
  }

  @Override
  public void start() {
    mPrimaryStage.setTitle(mMainInterface.title());
    mScene = new Scene(mMainInterface.mainPane());
    mScene.getStylesheets().add(JavelinSystem.stylesheet().file().toURI().toString());
    mPrimaryStage.setScene( mScene );
    mPrimaryStage.setWidth(  Integer.parseInt(mInitProps.getProperty( "javelinfx.maininterface.width", "1024" )) );
    mPrimaryStage.setHeight( Integer.parseInt(mInitProps.getProperty( "javelinfx.maininterface.height", "800" )) );
    mPrimaryStage.show();
    mMainInterface.start();
    return;
  }

  @Override
  public void stop() {
    mMainInterface.stop();
  }

  @Override
  public void destroy() {
  }

  @Override
  public void setPrimaryStage(Stage pPrimaryStage) {
    mPrimaryStage = pPrimaryStage;
    return;
  }


}
