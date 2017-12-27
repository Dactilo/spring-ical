package io.dactilo.sumbawa.spring.ical.converter.api.attendees;

public class ICalendarAttendee {
    private final String email;
    private final String name;

    public ICalendarAttendee(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
