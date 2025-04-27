package org.javelinfx.time;

import java.time.ZonedDateTime;

public interface ITIM_Time {

  ZonedDateTime UTCDateTime();
  long    UTCTime();
  String  toISO8601();
}
