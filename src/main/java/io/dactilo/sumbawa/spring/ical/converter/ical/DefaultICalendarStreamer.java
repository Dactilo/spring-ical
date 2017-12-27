package io.dactilo.sumbawa.spring.ical.converter.ical;

import io.dactilo.sumbawa.spring.ical.converter.api.ICalendar;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendee;
import io.dactilo.sumbawa.spring.ical.converter.api.ICalendarEvent;
import io.dactilo.sumbawa.spring.ical.converter.api.ICalendarStreamer;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URI;

public class DefaultICalendarStreamer implements ICalendarStreamer {


    @Override
    public void streamSpreadsheet(OutputStream outputStream,
                                  ICalendar iCalendar) throws IOException {
        final Calendar calendar = new Calendar();
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(new ProdId("-//Dactilo//Dactilo//EN"));

        for (ICalendarEvent iCalendarEvent : iCalendar.getEvents()) {
            final VEvent vEvent;

            if (iCalendarEvent.getAllDay() != null && iCalendarEvent.getAllDay()) {
                vEvent = new VEvent(new Date(iCalendarEvent.getStartDate()), iCalendarEvent.getName());
            } else {
                final DateTime start = new DateTime(iCalendarEvent.getStartDate());
                final DateTime end = new DateTime(iCalendarEvent.getEndDate());
                vEvent = new VEvent(start, end, iCalendarEvent.getName());
            }

            addUuid(vEvent, calendar);
            addAttendees(iCalendarEvent, vEvent);
            addChair(iCalendarEvent, vEvent);

            final CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, outputStream);
        }
    }

    private void addUuid(VEvent vEvent, Calendar calendar) throws SocketException {
        final UidGenerator uidGenerator = new UidGenerator("uidGen");
        vEvent.getProperties().add(uidGenerator.generateUid());
        calendar.getComponents().add(vEvent);
    }

    private void addAttendees(ICalendarEvent iCalendarEvent, VEvent vEvent) {
        for (ICalendarAttendee iCalendarAttendee : iCalendarEvent.getAttendees()) {
            final Attendee attendee = new Attendee(URI.create("mailto:" + iCalendarAttendee.getEmail()));
            attendee.getParameters().add(Role.REQ_PARTICIPANT);

            if (iCalendarEvent.getName() != null) {
                attendee.getParameters().add(new Cn(iCalendarEvent.getName()));
            }
            vEvent.getProperties().add(attendee);
        }
    }

    private void addChair(ICalendarEvent iCalendarEvent, VEvent vEvent) {
        if (iCalendarEvent.getChair() != null) {
            final Attendee chair = new Attendee(URI.create("mailto:" + iCalendarEvent.getChair().getEmail()));
            chair.getParameters().add(Role.CHAIR);

            if (iCalendarEvent.getChair().getName() != null) {
                chair.getParameters().add(new Cn(iCalendarEvent.getChair().getName()));
            }
            vEvent.getProperties().add(chair);
        }
    }
}
