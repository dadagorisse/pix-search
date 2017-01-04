package com.pix.search.dal.utils;

import com.google.common.collect.Range;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WhereClauseHelperTest {

    @Test
    public void testLowerBound() {
        String actual = WhereClauseHelper.localDateTimeConditions(
                "date", Range.atLeast(LocalDateTime.of(2016, 12, 1, 0, 0)),
                DateTimeFormatter.ISO_LOCAL_DATE);

        assertThat(actual, is(" WHERE date >= '2016-12-01' "));
    }

    @Test
    public void testUpperBound() {
        String actual = WhereClauseHelper.localDateTimeConditions(
                "date", Range.atMost(LocalDateTime.of(2016, 12, 1, 0, 0)),
                DateTimeFormatter.ISO_LOCAL_DATE);

        assertThat(actual, is(" WHERE date <= '2016-12-01' "));
    }

    @Test
    public void testRange() {
        String actual = WhereClauseHelper.localDateTimeConditions(
                "date", Range.closed(
                        LocalDateTime.of(2015, 11, 30, 0, 0),
                        LocalDateTime.of(2016, 12, 1, 0, 0)),
                DateTimeFormatter.ISO_LOCAL_DATE);

        assertThat(actual, is(" WHERE date >= '2015-11-30' AND date <= '2016-12-01' "));
    }
}
