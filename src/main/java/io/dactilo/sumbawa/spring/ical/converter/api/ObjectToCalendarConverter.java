package io.dactilo.sumbawa.spring.ical.converter.api;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Converts a list of Java Beans into a {@link ICalendarEvent} description.
 * This class allows to use the following annotation on the Java Bean getter description:
 * <ul>
 * <li>{@link ICalColumnIgnore}</li>
 * <li>{@link ICalEventName}</li>
 * <li>{@link ICalEventStartDate}</li>
 * <li>{@link ICalEventEndDate}</li>
 * <li>{@link ICalEventAllDay}</li>

 * </ul>
 */
public class ObjectToCalendarConverter implements CalendarConverter {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public ICalendar convert(List<?> input) {
        final List<ICalendarEvent> events =
                input.stream()
                        .map(this::convertValue)
                        .collect(Collectors.toList());

        return new ICalendar(events);
    }

    private ICalendarEvent convertValue(Object readRow) {
        final Class<?> clazz = readRow.getClass();
        final Map<String, Object> results = new LinkedHashMap<>();

        Method[] methods = clazz.getDeclaredMethods();

        String name = null;
        Date startDate = null;
        Date endDate = null;
        Boolean allDay = false;

        for (Method method : methods) {
            if (method.getAnnotation(ICalColumnIgnore.class) == null) {
                try {
                    Object methodResult = method.invoke(readRow);

                    ICalEventName iCalEventNameAnnotation = method.getAnnotation(ICalEventName.class);
                    if (iCalEventNameAnnotation != null) {
                        name = methodResult.toString();
                    }

                    ICalEventStartDate iCalEventStartDate = method.getAnnotation(ICalEventStartDate.class);
                    ICalEventAllDay iCalEventAllDay = method.getAnnotation(ICalEventAllDay.class);

                    if (iCalEventStartDate != null) {
                        if (methodResult instanceof Date) {
                            startDate = (Date) methodResult;
                        }

                        allDay = iCalEventAllDay != null;
                    }

                    ICalEventEndDate iCalEventEndDate = method.getAnnotation(ICalEventEndDate.class);
                    if (iCalEventEndDate != null) {
                        if (methodResult instanceof Date) {
                            endDate = (Date) methodResult;
                        }
                    }
                } catch (ReflectiveOperationException e) {
                    throw new IllegalArgumentException(e);
                }

            }
        }

        return new ICalendarEvent(startDate, endDate, allDay, name);
    }

}
