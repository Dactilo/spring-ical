package io.dactilo.sumbawa.spring.ical.converter.ical;

import io.dactilo.sumbawa.spring.ical.converter.api.AbstractICalendarStreamerTest;
import net.fortuna.ical4j.util.UidGenerator;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ICalendarStreamerTest extends AbstractICalendarStreamerTest {
    private final UidGenerator uidGenerator = new UidGenerator("uidGen");
    private final DefaultICalendarStreamer defaultICalendarStreamer = new DefaultICalendarStreamer(uidGenerator);

    public ICalendarStreamerTest() throws SocketException {
    }

    @Test
    public void testSimpleObject_conversionToICSIsSuccessful() throws ParseException, IOException {
        final List<ObjectWithUserExampleDTO> data = Arrays.asList(
                new ObjectWithUserExampleDTO("field 1", createDate(), createDate(), true, new ObjectWithUserExampleDTO.UserDTO("Mr Test", "test@test.fr")),
                new ObjectWithUserExampleDTO("field 1 2", createDate(), createDate(), false, new ObjectWithUserExampleDTO.UserDTO("Mr Test 2", "test2@test.fr"))
        );

        final String icsFile = new String(defaultICalendarStreamer.toByteArray(calendarConverter.convert(data)));
        final String[] results = icsFile.split("\r\n");

        assertEquals("BEGIN:VCALENDAR", results[0]);
        assertEquals("VERSION:2.0", results[1]);
        assertEquals("CALSCALE:GREGORIAN", results[2]);
        assertEquals("PRODID:-//Dactilo//Dactilo//EN", results[3]);
        assertEquals("SUMMARY:field 1", results[8]);

        assertThat(icsFile, containsString("test@test.fr"));
        assertThat(icsFile, containsString("Mr Test"));

        assertThat(icsFile, containsString("test2@test.fr"));
        assertThat(icsFile, containsString("Mr Test 2"));

    }

    @Test
    public void testSimpleObjectChairSerializer_conversionToICSIsSuccessful() throws ParseException, IOException {
        final List<ObjectExampleDTO> data = Arrays.asList(
                new ObjectExampleDTO("field 1", createDate(), createDate(), true, "test@test.fr"),
                new ObjectExampleDTO("field 1 2", createDate(), createDate(), false, "test@test.fr")
        );

        final String icsFile = new String(defaultICalendarStreamer.toByteArray(calendarConverter.convert(data)));
        final String[] results = icsFile.split("\r\n");

        assertEquals("BEGIN:VCALENDAR", results[0]);
        assertEquals("VERSION:2.0", results[1]);
        assertEquals("CALSCALE:GREGORIAN", results[2]);
        assertEquals("PRODID:-//Dactilo//Dactilo//EN", results[3]);
        assertEquals("SUMMARY:field 1", results[8]);

        assertThat(icsFile, containsString("test@test.fr"));
    }
}