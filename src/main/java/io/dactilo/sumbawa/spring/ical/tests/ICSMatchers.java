package io.dactilo.sumbawa.spring.ical.tests;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ICSMatchers {
    public static Matcher<String> isActuallyAnICSFile() {
        return new BaseMatcher<String>() {
            @Override
            public boolean matches(Object o) {
                final String icsContent = (String) o;

                final String[] lines = icsContent.split("\r\n");

                return "BEGIN:VCALENDAR".equals(lines[0]);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Given binary data corresponds to a ICS file");
            }
        };
    }
}
