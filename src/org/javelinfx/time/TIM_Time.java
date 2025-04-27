package org.javelinfx.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public record TIM_Time(long UTCTime ) implements ITIM_Time {

  static public final ZoneId UTCZONE = ZoneId.of( "UTC" );

  static public ZonedDateTime toZonedDateTime( long pUTC ) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(pUTC), UTCZONE);
  }

  static public ITIM_Time of( long pUTCTime ) {
    return new TIM_Time( pUTCTime );
  }

  @Override
  public ZonedDateTime UTCDateTime() {
    return toZonedDateTime(UTCTime);
  }


  @Override
  public String toISO8601() {
    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(UTCDateTime().toOffsetDateTime());
  }

}
