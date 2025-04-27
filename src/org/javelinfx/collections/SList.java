package org.javelinfx.collections;

import java.util.ArrayList;
import java.util.List;

public class SList {

  static public <V> List<V> list(List<V> pVs, V pV ) {
    List<V> l = new ArrayList<>(pVs);
    l.add(pV);
    return l;
  }

  static public <V> void addNoDuplicates( List<V> pL, List<V> pVs ) {
    for( var v : pVs ) {
      addNoDuplicate( pL, v );
    }
    return;
  }

  static public <V> void addNoDuplicate( List<V> pL, V pV ) {
    if (!pL.contains(pV)) {
      pL.add(pV);
    }
    return;
  }


  private SList() {
    return;
  }

}
