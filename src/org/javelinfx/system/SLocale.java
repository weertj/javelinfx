package org.javelinfx.system;

import java.util.Locale;
import java.util.ResourceBundle;

public class SLocale {

  static public final Locale DEFAULT = Locale.US;

  static private Locale APP_LOCALE = DEFAULT;

  static private ResourceBundle APP_BUNDLE = null;

  static public String resolveText( String pText ) {
    if (pText.startsWith("locale:")) {
      return appBundleKey(pText.substring("locale:".length()), pText);
    }
    return pText;
  }

  static public Locale appLocale() {
    return APP_LOCALE;
  }

  static public void appLocale( Locale pL ) {
    APP_LOCALE = pL;
    return;
  }

  static public ResourceBundle appBundle() {
    return APP_BUNDLE;
  }

  static public void appBundle( ResourceBundle pB ) {
    APP_BUNDLE = pB;
    return;
  }

  static public String appBundleKey( String pKey, String pDefault ) {
    if (appBundle()!=null && appBundle().containsKey(pKey)) {
      return appBundle().getString(pKey);
    }
    return pDefault;
  }

  private SLocale() {
  }

}
