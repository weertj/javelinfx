package org.javelinfx.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.javelinfx.common.C_Taglist;
import org.javelinfx.common.IC_Taglist;

import java.io.IOException;
import java.util.ResourceBundle;

public class FXMLLoad implements IFXMLLoad {

  static public FXMLLoad of() {
    return new FXMLLoad();
  }

  static public Parent root(IC_Taglist<String,Object> pTL ) {
    return (Parent)pTL.value( "Root" );
  }

  static public Object controller( IC_Taglist<String,Object> pTL ) {
    return pTL.value( "Controller" );
  }


  static public IC_Taglist<String,Object> toTags( FXMLLoader pLoader ) {
    IC_Taglist<String,Object> tags = C_Taglist.of();
    tags = tags.put( "Loader", pLoader );
//    tags = tags.put( "Pane", loader.load() );
    tags = tags.put( "Root", pLoader.getRoot() );
    tags = tags.put( "Controller", pLoader.getController() );
    return tags;
  }
  private FXMLLoad() {
    return;
  }

  @Override
  public IC_Taglist<String,Object> load(ClassLoader pCL, String pResource, ResourceBundle pBundle ) throws IOException {
    FXMLLoader loader = new FXMLLoader( this.getClass().getResource( pResource ), pBundle );
    if (pCL!=null) {
      loader.setClassLoader( pCL );
    }
    Parent parent = loader.load();
    var tags = toTags(loader);
    tags = tags.put( "Parent", parent );
    return tags;
  }

}
