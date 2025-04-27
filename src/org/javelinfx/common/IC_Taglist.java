package org.javelinfx.common;

import java.util.List;
import java.util.Optional;

public interface IC_Taglist<K,V> {


  boolean isMutable();
  boolean contains( K pK );
  boolean contains( K pK, V pV );

  IC_Taglist<K,V> put( K pK, V pV );
  V               value(K pKey );
  List<V>         values(K pKey );
  List<V>         values();

  IC_Taglist<K,V> remove( K pK );
  IC_Taglist<K,V> removeValue( K pK, V pV );

}
