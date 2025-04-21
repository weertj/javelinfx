package org.javelinfx.convert;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class IntegerConvert {

  static public int convert( Object pS, int pDefault ) {
    if (pS!=null) {
      if (pS instanceof String s) {
        if (!s.isEmpty()) {
          try {
            return Integer.parseInt(s);
          } catch( NumberFormatException ne ) {
            return pDefault;
          }
        }
      } else if (pS instanceof Number n) {
        return n.intValue();
      }

      if (pS instanceof Integer) {
        return (Integer)pS;
      } else if ((pS instanceof String) || (pS instanceof Character)) {
        String s = pS.toString().trim();
        // **** Prevent octal conversion
        while( s.startsWith( "0" )) {
          if (s.length()==1) {
            break;
          }
          s = s.substring(1);
        }
        return Integer.decode(s );
      } else if (pS instanceof Enum) {
        return ((Enum)pS).ordinal();
      } else if (pS instanceof Number) {
        return ((Number)pS).intValue();
      } else if (pS instanceof OptionalInt oi) {
        return oi.orElse(pDefault);
      } else if (pS instanceof Optional oi) {
        if (oi.isPresent()) {
          return convert(oi.get(),pDefault);
        }
        return pDefault;
      } else if (pS instanceof Boolean b) {
        return b ? 1 : 0;
      } else if (pS instanceof byte[] bstr) {
        return convert(new String(bstr),0);
      }

    }
    return pDefault;
  }


  private IntegerConvert() {
    return;
  }

}
