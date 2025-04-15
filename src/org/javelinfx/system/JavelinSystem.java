package org.javelinfx.system;

import org.javelinfx.filesystem.FS_File;
import org.javelinfx.filesystem.IFS_File;
import org.javelinfx.log.LOG_Handler;

import java.io.File;
import java.util.Locale;
import java.util.logging.Level;

public class JavelinSystem {

  static private       Locale   LOCALE = Locale.US;
  static private       String   OSSYS = "windows";
  static private final String   OS = System.getProperty("os.name").toLowerCase(Locale.US);
  static private       IFS_File WORKINGDIRECTORY = FS_File.of( new File(System.getProperty("user.dir")) );
  static private       String   APPNAME = "";

  static public Locale getLocale() {
    return LOCALE;
  }

  static public void setLocale(Locale pLocale) {
    LOCALE = pLocale;
  }

  static public String OSSYS() {
    return OSSYS;
  }

  static public boolean isWindows() {
    return (OS.contains("win"));
  }

  static public boolean isMac() {
    return (OS.contains("mac"));
  }

  static public boolean isUnix() {
    return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
  }

  static public boolean isSolaris() {
    return (OS.contains("sunos"));
  }

  static public IFS_File currentWorkingDirectory() {
    return WORKINGDIRECTORY;
  }

  static public int javaVersionNumber() {
    String ver = System.getProperty( "java.version" );
    if (ver.contains(".")) {
      String[] vers = ver.split("\\.");
      ver = vers[0];
    }
    return Integer.parseInt(ver);
  }

  static public IFS_File determineWorkingDirectory() {
    IFS_File wd = FS_File.of( new File(System.getProperty("user.dir")) );
    if ("/".equals(wd.canonicalPath())) {
      if (javaVersionNumber()>=16) {
        String jpath = System.getProperty("jpackage.app-path");
        IFS_File jhome = FS_File.of(jpath);
        wd = jhome.parent().parent().child( "app" );
      } else {
        String jpath = System.getProperty("java.library.path");
        if (jpath.contains(":")) {
          jpath = jpath.substring( 0, jpath.indexOf( ":" ) );
        }
        wd = FS_File.of(jpath);
      }
    }
    return wd;
  }

  static public IFS_File stylesheet() {
    return currentWorkingDirectory().child("data").child("css").child(APPNAME + "-" + OSSYS() + ".css");
  }

  static public void prepareForSystem( String pAppName ) {
    APPNAME = pAppName;
    if (isMac()) {
      OSSYS = "mac";
      // take the menu bar off the jframe
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      // set the name of the application menu item
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", pAppName );

      // ****
      if ("/".equals(currentWorkingDirectory().canonicalPath())) {
        WORKINGDIRECTORY = determineWorkingDirectory();
      }
    } else if (isSolaris()) {
      OSSYS = "solaris";
    } else if (isUnix()) {
      OSSYS = "unix";
    } else {
      OSSYS = "windows";
      System.setProperty("prism.lcdtext", "false");
      System.setProperty("prism.text", "t2k");
    }
    return;
  }

  static public void sleep( long pMillis ) {
    try {
      Thread.sleep(pMillis);
    } catch( InterruptedException e ) {
      LOG_Handler.log( "org.javelinfx.system.JavelinSystem.sleep", Level.FINER, e.getMessage(), e );
    }
  }

  private JavelinSystem() {
    return;
  }

}
