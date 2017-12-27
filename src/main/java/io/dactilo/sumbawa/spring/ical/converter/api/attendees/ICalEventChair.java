package io.dactilo.sumbawa.spring.ical.converter.api.attendees;

import io.dactilo.sumbawa.spring.ical.converter.api.ICalendarEvent;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies which getter represents the chair of an {@link ICalendarEvent}
 */
@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface ICalEventChair {

}