package io.dactilo.sumbawa.spring.ical.fiters.ical;

import io.dactilo.sumbawa.spring.ical.converter.api.ICalendarStreamer;
import io.dactilo.sumbawa.spring.ical.converter.api.ObjectToCalendarConverter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ICalendarHandlerMethodReturnValueHandler extends AbstractHttpMessageConverter<Object> {
    private final ICalendarStreamer calendarStreamer;
    private final ObjectToCalendarConverter objectToCalendarConverter;

    ICalendarHandlerMethodReturnValueHandler(ICalendarStreamer calendarStreamer, ObjectToCalendarConverter objectToCalendarConverter) {
        super(new MediaType("text", "calendar"));
        this.calendarStreamer = calendarStreamer;
        this.objectToCalendarConverter = objectToCalendarConverter;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Calendar Parsing is not (yet) implemented");
    }

    @Override
    protected void writeInternal(Object input, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (!(input instanceof List)) {
            calendarStreamer.streamSpreadsheet(outputMessage.getBody(), objectToCalendarConverter.convert(Collections.singletonList(input)));
        } else {
            calendarStreamer.streamSpreadsheet(outputMessage.getBody(), objectToCalendarConverter.convert((List<Object>) input));
        }
    }
}
