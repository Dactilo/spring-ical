package io.dactilo.sumbawa.spring.ical.converter.api;

import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalEventAttendee;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalEventChair;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendee;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendeeTransformer;
import org.apache.commons.text.StringEscapeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Converts a list of Java Beans into a {@link ICalendarEvent} description.
 * This class allows to use the following annotation on the Java Bean getter description:
 * <ul>
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

        Method[] methods = clazz.getDeclaredMethods();

        String name = null;
        Date startDate = null;
        Date endDate = null;
        Boolean allDay = false;
        ICalendarAttendee chair = null;
        List<ICalendarAttendee> attendees = new ArrayList<>();

        for (Method method : methods) {
            if (supportICalendar(method)) {
                try {
                    Object methodResult = method.invoke(readRow);

                    ICalEventName iCalEventNameAnnotation = method.getAnnotation(ICalEventName.class);
                    if (iCalEventNameAnnotation != null) {
                        name = stripHtml(methodResult.toString());
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

                    ICalEventChair iCalEventChair = method.getAnnotation(ICalEventChair.class);
                    if (iCalEventChair != null) {
                        if(methodResult != null) {
                            chair = createICalEventAttendeeInstanceFromField(method, methodResult);
                        }
                    }

                    ICalEventAttendee iCalEventAttendee = method.getAnnotation(ICalEventAttendee.class);
                    if (iCalEventAttendee != null) {
                        if(methodResult instanceof List) {
                            for (Object attendee : ((List) methodResult)) {
                                attendees.add(createICalEventAttendeeInstanceFromField(method, attendee));
                            }
                        } else if(methodResult instanceof String) {
                            attendees.add(createICalEventAttendeeInstanceFromField(method, methodResult));
                        }
                    }
                } catch (ReflectiveOperationException e) {
                    throw new IllegalArgumentException(e);
                }

            }
        }

        return new ICalendarEvent(startDate, endDate, allDay, name, attendees, chair);
    }

    private String stripHtml(String input) {
        return StringEscapeUtils.unescapeHtml4(input.replaceAll("\\<[^>]*>","")).trim();
    }

    private boolean supportICalendar(Method method) {
        final List<Class<? extends Annotation>> supportedAnnotations = Arrays.asList(
                ICalEventName.class,
                ICalEventAllDay.class,
                ICalEventStartDate.class,
                ICalEventEndDate.class,
                ICalEventChair.class,
                ICalEventAttendee.class
        );

        for (Class<? extends Annotation> supportedAnnotation : supportedAnnotations) {
            if(method.getAnnotation(supportedAnnotation) != null) {
                return true;
            }
        }

        return false;
    }

    private ICalendarAttendee createICalEventAttendeeInstanceFromField(Method method, Object attendeeToBeTransformed) throws ReflectiveOperationException {
        final ICalendarAttendee result;
        final ICalendarAttendeeTransformer iCalendarAttendeeTransformer = method.getAnnotation(ICalendarAttendeeTransformer.class);

        if(iCalendarAttendeeTransformer == null) {
            result = new ICalendarAttendee(attendeeToBeTransformed.toString(), null);
        } else {
            result = iCalendarAttendeeTransformer.transformer().getConstructor().newInstance().transform(attendeeToBeTransformed);
        }

        return result;
    }

}
