package io.dactilo.sumbawa.spring.ical.converter.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Serialize and send a {@link ICalendar} into a stream.
 */
public interface ICalendarStreamer {
    /**
     * Serialize and send a {@link ICalendar} into a stream.
     *
     * @param outputStream The stream to send the results to
     * @param iCalendar The iCalendar
     * @throws IOException If any IO error occurs
     */
    void streamSpreadsheet(OutputStream outputStream, ICalendar iCalendar) throws IOException;

    default byte[] toByteArray(ICalendar iCalendar) throws IOException {
        try(final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            streamSpreadsheet(byteArrayOutputStream, iCalendar);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
