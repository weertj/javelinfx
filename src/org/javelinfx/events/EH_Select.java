package org.javelinfx.events;

import java.util.Optional;

public enum EH_Select {

  UNKNOWN( -1.0 ),
  ALWAYSOFF( 0.0 ),
  HARDOFF( 0.1 ),
  OFF(0.2 ),
  MAYBE(0.5 ),
  ON( 0.8 ),
  HARDON( 0.9 ),
  ALWAYSON( 1.0 )
  ;

  static public EH_Select from(Optional<?> pSelect ) {
    if (pSelect==null) {
      return ALWAYSOFF;
    }
    if (!pSelect.isPresent()) {
      return HARDOFF;
    }
    if (pSelect.get() instanceof EH_Select select) {
      return select;
    }
    throw new UnsupportedOperationException(pSelect.get().getClass().getName());
  }


  static public EH_Select from( boolean pB ) {
    return pB ? ON : OFF;
  }

  private final double mSelect;

  EH_Select( double pSel ) {
    mSelect = pSel;
    return;
  }

  public EH_Select bestNew( EH_Select pNewSel ) {
    boolean asnew = pNewSel.asBool();
    if (asBool()==asnew) {
      // **** The same
      if (asnew) {
        return max(pNewSel);
      } else {
        return min(pNewSel);
      }
    } else {
      // **** switch
      if (this==HARDON) {
        if (asnew) {
          return this;
        } else if (pNewSel==HARDOFF) {
          return pNewSel;
        } else {
          return this;
        }
      }
      return pNewSel;
    }
  }

  public EH_Select min( EH_Select pSel ) {
    return pSel.value()<value() ? pSel : this;
  }

  public EH_Select max( EH_Select pSel ) {
    return pSel.value()>value() ? pSel : this;
  }

  public double value() {
    return mSelect;
  }

  public boolean asBool() {
    if (mSelect<0.5) {
      return false;
    }
    if (mSelect>0.5) {
      return true;
    }
    return false;
  }

}
