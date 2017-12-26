package io.dactilo.sumbawa.spring.ical.converter.ical;

import io.dactilo.sumbawa.spring.ical.converter.api.AbstractICalendarStreamerTest;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ICalendarStreamerTest extends AbstractICalendarStreamerTest {
    private final DefaultICalendarStreamer defaultICalendarStreamer = new DefaultICalendarStreamer();

    @Test
    public void testSimpleObject_conversionToCSVIsSuccessful() throws ParseException, IOException {
        final List<ObjectExampleDTO> data = Arrays.asList(
                new ObjectExampleDTO("field 1", createDate(), createDate(), true),
                new ObjectExampleDTO("field 1 2", createDate(), createDate(), false)
        );

        final String[] results = new String(defaultICalendarStreamer.toByteArray(calendarConverter.convert(data))).split("\r\n");

        assertEquals("BEGIN:VCALENDAR", results[0]);
        assertEquals("VERSION:2.0", results[1]);
        assertEquals("CALSCALE:GREGORIAN", results[2]);
        assertEquals("PRODID:-//Dactilo//Dactilo//EN", results[3]);
        assertEquals("SUMMARY:field 1", results[8]);
    }

}