package org.javelinfx.common;

public interface IC_Entity {

  default String entityType() {
    return getClass().getSimpleName();
  }
  String id();
  String name();

}
