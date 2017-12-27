package io.dactilo.sumbawa.spring.ical.converter.api.attendees;

public interface ICalendarAttendeeTransformerFunction<T> {
    ICalendarAttendee transform(T input);
}
