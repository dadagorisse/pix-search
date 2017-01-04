package com.pix.search.dal.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public final class TimesHelper {

    public static LocalDateTime fromTimeStamp(long epochInSecond) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(epochInSecond),
                ZoneOffset.UTC);
    }

    public static long toTimeStamp(LocalDateTime date) {
        return date.toInstant(ZoneOffset.UTC).getEpochSecond();
    }
}
