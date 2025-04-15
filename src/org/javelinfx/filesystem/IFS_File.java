package org.javelinfx.filesystem;

import org.javelinfx.log.LOG_Handler;

import java.io.*;
import java.util.logging.Level;

public interface IFS_File {

  File file();

  default boolean exists() {
    return file().exists();
  }

  default InputStream inputstream() throws FileNotFoundException {
    return new FileInputStream(file());
  }

  IFS_File child( String pName );
  IFS_File parent();

  default String canonicalPath() {
    try {
      return file().getCanonicalPath();
    } catch( IOException e ) {
      LOG_Handler.log("org.javelinfx.filesystem.IFS_File", Level.WARNING,null,e);
    }
    return "";
  }


}
