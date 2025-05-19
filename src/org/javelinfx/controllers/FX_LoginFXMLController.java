/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javelinfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.javelinfx.colors.SUX_Colors;
import org.javelinfx.common.C_Taglist;
import org.javelinfx.common.IC_Taglist;
import org.javelinfx.fxml.FXMLLoad;
import org.javelinfx.log.LOG_Handler;
import org.javelinfx.system.FX_Platform;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * FXML Controller class
 *
 * @author weert
 */
public class FX_LoginFXMLController implements Initializable {

  private Stage mStage;
  private boolean mLoginOk = false;
 
  @FXML
  private ImageView mLogo;
  @FXML
  private CheckBox mRemember;
  @FXML
  private Button mOk;
  @FXML
  private Button mCancel;
  @FXML
  private Label mServerName;
  @FXML
  private TextField mServer;
  @FXML
  private TextField mUsername;
  @FXML
  private PasswordField mPassword;
  @FXML
  private Text mErrorText;
  
  private Image mAppLogo;
  
  /**
   * Initializes the controller class.
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    editServer( false );
    mPassword.setOnAction( e -> {
      mOk.fire();
    });
    mOk.setOnAction( e -> {
      mLoginOk = true;
      mStage.close();
    });
    mCancel.setOnAction( e -> {
      mLoginOk = false;
      mStage.close();
    });
    setErrorText("");
    return;
  }

  public void setLogo( Image pLogo ) {
    mAppLogo = pLogo;
    mLogo.setImage( mAppLogo );
    return;
  }
  
  public Image getLogo() {
    return mAppLogo;
  }
  
  public void setErrorText( String pError ) {
    if (pError.isEmpty()) {
      mErrorText.setVisible( false );
    } else {
      mErrorText.setText( pError );
      mErrorText.setFill(SUX_Colors.ERROR );
      mErrorText.setVisible( true );
    }
    return;
  }
  
//  public void loginServerIs( boolean pAvailable ) {
//    setErrorText( "Connection failed: Server not available" );
//    if (pAvailable) {
//      mErrorText.setVisible( false );
//    } else {
//      mErrorText.setText( "ERROR: Server not available" );
//      mErrorText.setFill( FX_Colors.JESTER_RED );
//      mErrorText.setVisible( true );
//    }
//    return;
//  }
//  
  public void editServer( boolean pS ) {
    mServer.setVisible( pS );
    mServerName.setVisible( pS );
    mServer.setEditable(pS);
    return;
  }
  
  /**
   * start
   * @param pProduct
   * @param pStorageKey
   * @param pCustomCss "" for default
   * @return taglist keys = "Username", "Password", "Remember"
   *                 isEmpty() when cancelled
   */
  static public IC_Taglist<String,String> start(String pProduct, String pStorageKey ) {
    return start( pProduct, pStorageKey, "", (lc)->{} );
  }
  
  static public IC_Taglist<String,String> start( String pProduct, String pStorageKey, String pCSS, Consumer<FX_LoginFXMLController> pCB ) {
    IC_Taglist result = C_Taglist.ofMutable();
    Runnable fxrunner = () -> {
      try {
        IC_Taglist<String,Object> tags = FXMLLoad.of().load(null,"/org/javelinfx/controllers/FX_LoginFXML.fxml",null);
        Scene scene = new Scene((Parent)tags.value("Root"));
        if (pCSS.isEmpty()) {
        } else {
          scene.getStylesheets().clear();
          scene.getStylesheets().add( pCSS );
        }
        FX_LoginFXMLController lc = (FX_LoginFXMLController)tags.value( "Controller" );
        pCB.accept(lc);
        Stage loginstage = new Stage(StageStyle.DECORATED);
        lc.mStage = loginstage;
        if (lc.getLogo()!=null) {
          loginstage.getIcons().add( lc.getLogo() );
        }
        loginstage.setTitle( "Login" );
        loginstage.setScene(scene);
        loginstage.setAlwaysOnTop(true);
        loginstage.initModality( Modality.APPLICATION_MODAL );
        loginstage.showAndWait();
        if (lc.mLoginOk) {
          result.put( "Username", lc.mUsername.getText())
                .put( "Password", lc.mPassword.getText());
        }
//        if (lc.mRemember.isSelected()) {
//          result.put( "Remember", "true" );
//        }
      } catch (Throwable e) {
        LOG_Handler.log("org.javelinfx.controllers.FX_LoginFXMLController", Level.SEVERE,null,e);
      }
    };
    FX_Platform.runLater( fxrunner );
    return result;
  }

}
