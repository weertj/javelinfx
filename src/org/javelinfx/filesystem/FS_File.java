package org.javelinfx.filesystem;

import org.javelinfx.system.JavelinSystem;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class FS_File implements IFS_File {

  static public boolean isAbsoluteFilePath( String pFile ) {
    if (pFile.startsWith("/")) {
      return true;
    }
    if (pFile.length()>1) {
      return pFile.charAt(1) == ':';
    }
    return false;
  }

  static public Optional<File> toFile(URI pURI ) {
    if (pURI.getScheme().equals("file")) {
      if (JavelinSystem.isWindows()) {
        String file = pURI.toString().substring( "file:/".length() );
        file = file.replace( "%20", " " );
        return Optional.of(new File( file ));
      }
      if (JavelinSystem.isMac()) {
        String file = pURI.toString().substring( "file:".length() );
        file = file.replace( "%20", " " );
        return Optional.of(new File( file ));
      }
      if (JavelinSystem.isUnix()) {
        String file = pURI.toString().substring( "file:".length() );
        file = file.replace( "%20", " " );
        return Optional.of(new File( file ));
      }
      throw new UnsupportedOperationException();
    }

    return Optional.empty();
  }

  static public IFS_File of( String pFile ) {
    if (pFile.startsWith( "file:" )) {
      try {
        URI uri = new URL(pFile).toURI();
        var ofile = toFile( uri );
        if (ofile.isPresent()) {
          return of( ofile.get() );
        } else {
          throw new MalformedURLException(pFile);
        }
      } catch(MalformedURLException | URISyntaxException e ) {
//        LOG_Handler.log( "org.javelinfx.filesystem.FS_File", Level.WARNING, e.getMessage(),e );
        throw new RuntimeException(e);
      }
    } else {
      if (isAbsoluteFilePath(pFile)) {
        return of(pFile);
      } else {
        File f = new File(pFile );
        if (f.exists()) {
          return of(f);
        }
        return JavelinSystem.currentWorkingDirectory().child( pFile );
      }
    }
  }

  static public IFS_File of( File pFile ) {
    return new FS_File( pFile );
  }

  private final File mFile;

  private FS_File( File pFile ) {
    mFile = pFile;
    return;
  }

  @Override
  public File file() {
    return mFile;
  }

  @Override
  public IFS_File parent() {
    File parent = file().getParentFile();
    if (parent==null) {
      return JavelinSystem.determineWorkingDirectory();
    }
    return of( parent );
  }


  @Override
  public IFS_File child(String pName) {
    return new FS_File( new File(file(),pName) );
  }

}
