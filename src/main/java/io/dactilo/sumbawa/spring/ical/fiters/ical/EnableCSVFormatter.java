package io.dactilo.sumbawa.spring.ical.fiters.ical;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Add this annotation to your spring web configuration to enable CSV support
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ICalendarFormatterConfiguration.class})
public @interface EnableCSVFormatter {
}
