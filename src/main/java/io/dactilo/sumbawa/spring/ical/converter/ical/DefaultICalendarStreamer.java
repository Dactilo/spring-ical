package io.dactilo.sumbawa.spring.ical.converter.ical;

import io.dactilo.sumbawa.spring.ical.converter.api.ICalendar;
import io.dactilo.sumbawa.spring.ical.converter.api.ICalendarEvent;
import io.dactilo.sumbawa.spring.ical.converter.api.ICalendarStreamer;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultICalendarStreamer implements ICalendarStreamer {


    @Override
    public void streamSpreadsheet(OutputStream outputStream,
                                  ICalendar iCalendar) throws IOException {
        final Calendar calendar = new Calendar();
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(new ProdId("-//Dactilo//Dactilo//EN"));

        for (ICalendarEvent iCalendarEvent : iCalendar.getEvents()) {

            if(iCalendarEvent.getAllDay() != null && iCalendarEvent.getAllDay()) {
                final VEvent vEvent = new VEvent(new Date(iCalendarEvent.getStartDate()), iCalendarEvent.getName());
                final UidGenerator uidGenerator = new UidGenerator("uidGen");
                vEvent.getProperties().add(uidGenerator.generateUid());
                calendar.getComponents().add(vEvent);
            } else {
                final DateTime start = new DateTime(iCalendarEvent.getStartDate());
                final DateTime end = new DateTime(iCalendarEvent.getEndDate());
                final VEvent vEvent = new VEvent(start, end, iCalendarEvent.getName());
                final UidGenerator uidGenerator = new UidGenerator("uidGen");
                vEvent.getProperties().add(uidGenerator.generateUid());
                calendar.getComponents().add(vEvent);
            }


            final CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, outputStream);
        }
    }
}
