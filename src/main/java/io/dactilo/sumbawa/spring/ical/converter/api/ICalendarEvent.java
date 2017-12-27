package io.dactilo.sumbawa.spring.ical.converter.api;

import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendee;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ICalendarEvent {
    private final Date startDate;
    private final Date endDate;
    private final Boolean allDay;
    private final String name;
    private final List<ICalendarAttendee> attendees;
    private final ICalendarAttendee chair;

    public ICalendarEvent(Date startDate,
                          Date endDate, Boolean allDay,
                          String name,
                          List<ICalendarAttendee> attendees,
                          ICalendarAttendee chair) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
        this.name = name;
        this.attendees = Collections.unmodifiableList(attendees);
        this.chair = chair;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public String getName() {
        return name;
    }

    public List<ICalendarAttendee> getAttendees() {
        return attendees;
    }

    public ICalendarAttendee getChair() {
        return chair;
    }
}
