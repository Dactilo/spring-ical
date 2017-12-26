package io.dactilo.sumbawa.spring.ical.converter.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * If a getter is annotated with {@link ICalColumnIgnore},
 * the field won't appear in the resulting {@link Spreadsheet}
 */
@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface ICalColumnIgnore {

}