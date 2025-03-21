package org.javelinfx.engine;

import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public interface IJavelinFX {

  void init( Properties pProps ) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

  void start();
  void stop();

  void destroy();

  void setPrimaryStage( Stage pPrimaryStage );

}
