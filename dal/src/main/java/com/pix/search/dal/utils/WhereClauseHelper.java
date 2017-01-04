package com.pix.search.dal.utils;

import com.google.common.collect.Range;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public final class WhereClauseHelper {

    public static <C extends Comparable> String conditions(
            String column, Range<C> range, Function<C, String> boundToString) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE ");
        if (range.hasLowerBound()) {
            sb.append(String.format("%s >= '%s' ", column,
                    boundToString.apply(range.lowerEndpoint())));
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            sb.append("AND ");
        }
        if (range.hasUpperBound()) {
            sb.append(String.format("%s <= '%s' ", column,
                    boundToString.apply(range.upperEndpoint())));
        }
        return sb.toString();
    }

    public static String localDateTimeConditions(
            String column, Range<LocalDateTime> range, DateTimeFormatter formatter) {
        return conditions(column, range, d -> formatter.format(d));
    }
}
