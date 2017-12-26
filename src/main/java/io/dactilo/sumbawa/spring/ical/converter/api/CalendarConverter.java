package io.dactilo.sumbawa.spring.ical.converter.api;

import java.util.List;

/**
 * Converts a list Java Beans into a {@link ICalendar} description
 */
public interface CalendarConverter {
    /**
     * Converts a given input into a {@link ICalendar} document
     * @param input The input to be converted
     * @return The {@link ICalendar} document
     */
    ICalendar convert(List<?> input);
}
