package org.javelinfx.engine;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.javelinfx.filesystem.IFS_File;

import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class JavelinFX implements IJavelinFX, SystemEventListener {

  private Properties      mInitProps;
  private Stage           mPrimaryStage;
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
    mPrimaryStage.setScene( new Scene(mMainInterface.mainPane()));
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
