package org.javelinfx.fxml;

import org.javelinfx.common.IC_Taglist;

import java.io.IOException;
import java.util.ResourceBundle;

public interface IFXMLLoad {

  IC_Taglist<String,Object> load(ClassLoader pCL, String pResource, ResourceBundle pBundle ) throws IOException;

}
