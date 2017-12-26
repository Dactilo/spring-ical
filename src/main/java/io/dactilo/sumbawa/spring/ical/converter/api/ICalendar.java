package io.dactilo.sumbawa.spring.ical.converter.api;

import java.util.Collections;
import java.util.List;

/**
 * Description of a calendar document
 */
public class ICalendar {
    private final List<ICalendarEvent> events;

    public ICalendar(List<ICalendarEvent> events) {
        this.events = events;
    }

    /**
     * The first row (i.e. The header)
     * @return The header
     */
    public List<ICalendarEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }
}
