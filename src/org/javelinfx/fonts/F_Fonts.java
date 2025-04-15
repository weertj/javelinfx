/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javelinfx.fonts;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.text.Font;
import org.javelinfx.common.C_Taglist;
import org.javelinfx.common.IC_Taglist;
import org.javelinfx.filesystem.FS_File;
import org.javelinfx.filesystem.IFS_File;

/**
 *
 * @author weertj
 */
@SuppressWarnings("ClassWithoutLogger")
public class F_Fonts {
  
  static private final F_Fonts DEFAULT = of();

  static public Font rescale( Font pFont, double pScale ) {
    return Font.font(pFont.getFamily(),pFont.getSize()*pScale);
  }

  static public F_Fonts getDefault() {
    return DEFAULT;
  }
  
  static public F_Fonts of() {
    return new F_Fonts();
  }
   
  @SuppressWarnings("unchecked")
  private final IC_Taglist<String, ObjectProperty<Font>> mFonts = C_Taglist.ofMutable();

  private F_Fonts() {
    return;
  }

  public Font fontByKey( String pKey ) {
    ObjectProperty<Font> obj = font(pKey);
    if (obj==null) {
      return null;
    }
    return obj.get();
  }

  public ObjectProperty<Font> font( String pKey ) {
    return mFonts.value(pKey);
  }

  public void putFont( String pKey, Font pFont ) {
    mFonts.put( pKey, new SimpleObjectProperty<>(pFont) );
    return;
  }
  
  /**
   * loadFontsFromDirectory
   * @param pFontDir
   * @param pFontProperties
   * @param pMagnify
   * @throws IOException 
   */
  public F_Fonts loadFontsFromDirectory(IFS_File pFontDir, final Properties pFontProperties, double pMagnify ) throws IOException {
    for( Object key : pFontProperties.keySet() ) {
      String skey = (String)key;
      String[] keys = skey.split( "-" );
      if ("Font".equals(keys[1])) {
        double size = Double.parseDouble(pFontProperties.getProperty( keys[0] + "-Size", "1" )) * pMagnify;
        IFS_File fontFile = FS_File.of( pFontDir.file().getAbsolutePath() + "/" + pFontProperties.getProperty( keys[0] + "-Font" ) );
        Font f;
        try (InputStream is = fontFile.inputstream()) {
          f = Font.loadFont( is, size );
        }
        putFont( pFontProperties.getProperty( keys[0] + "-Key" ).trim(), f );
      }
    }
    return this;
  }

  public F_Fonts loadFontsFromResource(Class<?> pResourceBase, final Properties pFontProperties, double pMagnify ) throws IOException {
    for( Object key : pFontProperties.keySet() ) {
      String skey = (String)key;
      String[] keys = skey.split( "-" );
      if ("Font".equals(keys[1])) {
        double size = Double.parseDouble(pFontProperties.getProperty( keys[0] + "-Size", "1" )) * pMagnify;
        Font f;
        try (InputStream is = pResourceBase.getResourceAsStream( pFontProperties.getProperty( keys[0] + "-Font" ))) {
          f = Font.loadFont( is, size );
          if (f==null) {
            f = Font.font( pFontProperties.getProperty( keys[0] + "-Font" ), size );
          }
        }
        putFont( pFontProperties.getProperty( keys[0] + "-Key" ).trim(), f );
      }
    }
    return this;
  }

  
}
