/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javelinfx.system;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.javelinfx.log.LOG_Handler;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

/**
 *
 * @author weert
 */
@SuppressWarnings("ClassWithoutLogger")
public class FX_Platform {
  
  static public boolean FXISRUNNING = true;
  
  static public boolean isFxApplicationThread() {
    return Platform.isFxApplicationThread();
  }
  
  /**
   * runLater
   * @param pR 
   */
  static public void runLater( Runnable pR ) {
    if (FXISRUNNING) {
      if (isFxApplicationThread()) {
        pR.run();
      } else {
        try {
          Platform.runLater(pR);
        } catch( IllegalStateException e ) {
          LOG_Handler.log( "org.javelinfx.system.FX_Platform.runLater", Level.FINER, e.getMessage(), e );
        }
      }
    } else {
      pR.run();      
    }
    return;
  }

  static public void runLaterForced( Runnable pR ) {
    if (FXISRUNNING) {
      try {
        Platform.runLater(pR);
      } catch( IllegalStateException e ) {
        LOG_Handler.log( "org.javelinfx.system.FX_Platform.runLaterForced", Level.FINER, e.getMessage(), e );
      }
    } else {
      pR.run();      
    }
    return;
  }

  static public Thread runLaterAfter( Runnable pR, long pMillis ) {
    Thread t = new Thread( () -> {
        JavelinSystem.sleep(pMillis);
        runLaterForced( pR );
      });
    t.start();
    return t;
  }
  
  static public void setupJavaFX() throws RuntimeException {
    final CountDownLatch latch = new CountDownLatch(1);
    SwingUtilities.invokeLater(() -> {
        new JFXPanel();
        latch.countDown();
    });

    try {
        latch.await();
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }


  private FX_Platform() {
  }
  
}
