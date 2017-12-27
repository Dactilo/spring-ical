package io.dactilo.sumbawa.spring.ical.converter.api.attendees;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface ICalendarAttendeeTransformer {
    Class<? extends ICalendarAttendeeTransformerFunction> transformer();
}