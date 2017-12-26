package io.dactilo.sumbawa.spring.ical.converter.api;

import java.util.Date;

public class ICalendarEvent {
    private final Date startDate;
    private final Date endDate;
    private final Boolean allDay;
    private final String name;

    public ICalendarEvent(Date startDate,
                          Date endDate, Boolean allDay, String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
        this.name = name;
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
}
