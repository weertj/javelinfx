package org.javelinfx.common;

import java.util.*;

public class C_Taglist<K,V> implements IC_Taglist<K,V> {

  static public <K,V> IC_Taglist<K,V> of() {
    return new C_Taglist<>(Map.of(),false);
  }

  static public <K,V> IC_Taglist<K,V> ofMutable() {
    return new C_Taglist<>(new HashMap<>(),true);
  }

  static private <K,V> IC_Taglist<K,V> add( C_Taglist<K,V> pTL, K pK, V pV ) {
    synchronized(pTL.mHash) {
      if (pTL.isMutable()) {
        if (pTL.mHash.containsKey(pK)) {
          Object v = pTL.mHash.get(pK);
          if (v instanceof List mlist) {
            mlist.add(pV);
          } else {
            List<Object> l = new ArrayList<>(4);
            l.add(v);
            l.add(pV);
            pTL.mHash.put(pK, l);
          }
        } else {
          pTL.mHash.put(pK, pV);
        }
        return pTL;
      } else {
        // **** Immutable
        HashMap<K, Object> map = new HashMap<>(pTL.mHash);
        if (map.containsKey(pK)) {
          Object v = pTL.mHash.get(pK);
          if (v instanceof List mlist) {
            mlist.add(pV);
          } else {
            List<Object> l = new ArrayList<>(4);
            l.add(v);
            l.add(pV);
            map.put(pK, l);
          }
        } else {
          map.put(pK, pV);
        }
        return new C_Taglist<>(map, false);
      }
    }
  }

  /**
   * remove
   * @param pTL
   * @param pK
   * @return
   */
  static private <K,V> IC_Taglist<K,V> remove( C_Taglist<K,V> pTL, K pK ) {
    synchronized (pTL.mHash) {
      if (pTL.isMutable()) {
        pTL.mHash.remove(pK);
      } else {
        // **** Immutable
        if (pTL.contains(pK)) {
          Map<K, Object> map = new HashMap<>(pTL.mHash);
          map.remove(pK);
          return new C_Taglist<>(map, false);
        }
      }
      return pTL;
    }
  }

  static private <K,V> IC_Taglist<K,V> remove( C_Taglist<K,V> pTL, K pK, V pV ) {
    if (pTL.isMutable()) {
      var v = pTL.mHash.get(pK);
      if (v instanceof List list) {
        list.remove(pV);
      } else {
        pTL.mHash.remove(pK);
      }
    } else {
      // **** Immutable
      if (pTL.contains(pK)) {
        Map<K,Object> map = new HashMap<>(pTL.mHash);
        var v = map.get(pK);
        if (v instanceof List list) {
          list.remove(pV);
        } else {
          map.remove(pK);
        }
        map.remove(pK);
        return new C_Taglist<>(map, false);
      }
    }
    return pTL;
  }

  private final Map<K, Object> mHash;

  private C_Taglist( Map<K,Object> pHash, boolean pMutable ) {
    if (pMutable) {
      mHash = pHash;
    } else {
      mHash = Collections.unmodifiableMap(pHash);
    }
    return;
  }

  @Override
  public boolean isMutable() {
    return mHash instanceof HashMap;
  }

  @Override
  public boolean contains(K pK) {
    synchronized (mHash) {
      return mHash.containsKey(pK);
    }
  }

  @Override
  public boolean contains(K pK, V pV) {
    synchronized (mHash) {
      var lv = mHash.get(pK);
      if (lv == null) {
        return false;
      } else {
        if (lv instanceof List list) {
          return list.contains(pV);
        } else {
          return Objects.equals(pV, lv);
        }
      }
    }
  }

  @Override
  public IC_Taglist<K,V> put( K pK, V pV ) {
    return add( this, pK, pV );
  }

  @Override
  public List<V> values(K pKey) {
    synchronized (mHash) {
      var val = mHash.get(pKey);
      if (val instanceof List l) {
        return l;
      }
      return List.of((V) val);
    }
  }

  @Override
  public V value(K pKey) {
    synchronized (mHash) {
      var val = mHash.get(pKey);
      if (val instanceof List l) {
        return (V) l.getFirst();
      }
      return (V) val;
    }
  }

  @Override
  public IC_Taglist<K,V> remove( K pK ) {
    return remove( this, pK );
  }

  @Override
  public IC_Taglist<K, V> removeValue(K pK, V pV) {
    return remove(this,pK,pV);
  }

}
