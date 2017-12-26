package io.dactilo.sumbawa.spring.ical.converter.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractICalendarStreamerTest {
    protected final CalendarConverter calendarConverter = new ObjectToCalendarConverter();

    protected Date createDate() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse("2006-04-05");
    }

    public static class ObjectExampleDTO {
        private final String field1;
        private final Date field2;
        private final Date field3;
        private final boolean field4;

        @JsonCreator
        public ObjectExampleDTO(@JsonProperty("field1") String field1,
                                           @JsonProperty("field2") Date field2,
                                           @JsonProperty("field3") Date field3,
                                           @JsonProperty("field4") boolean field4) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
        }

        @ICalEventName
        public String getField1() {
            return field1;
        }

        @ICalEventEndDate
        public Date getField2() {
            return field2;
        }

        @ICalEventStartDate
        public Date getField3() {
            return field3;
        }

        public boolean isField4() {
            return field4;
        }
    }

}