package io.dactilo.sumbawa.spring.ical.converter.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalEventChair;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendee;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendeeTransformer;
import io.dactilo.sumbawa.spring.ical.converter.api.attendees.ICalendarAttendeeTransformerFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractICalendarStreamerTest {
    protected final CalendarConverter calendarConverter = new ObjectToCalendarConverter();

    protected Date createDate() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse("2006-04-05");
    }

    public static class ObjectWithUserExampleDTO {
        private final String field1;
        private final Date field2;
        private final Date field3;
        private final boolean field4;
        private final UserDTO organizer;

        @JsonCreator
        public ObjectWithUserExampleDTO(@JsonProperty("field1") String field1,
                                        @JsonProperty("field2") Date field2,
                                        @JsonProperty("field3") Date field3,
                                        @JsonProperty("field4") boolean field4,
                                        @JsonProperty("organizer") UserDTO organizer) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.organizer = organizer;
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

        @JsonIgnore
        @ICalEventChair
        @ICalendarAttendeeTransformer(transformer = UserDTO.Transformer.class)
        public UserDTO organizer() {
            return organizer;
        }

        public static class UserDTO {
            private final String name;
            private final String email;

            public UserDTO(String name, String email) {
                this.name = name;
                this.email = email;
            }

            public String getName() {
                return name;
            }

            public String getEmail() {
                return email;
            }

            public static class Transformer implements ICalendarAttendeeTransformerFunction<UserDTO> {
                @Override
                public ICalendarAttendee transform(UserDTO input) {
                    return new ICalendarAttendee(input.email, input.name);
                }
            }
        }
    }

    public static class ObjectExampleDTO {
        private final String field1;
        private final Date field2;
        private final Date field3;
        private final boolean field4;
        private final String organizer;

        @JsonCreator
        public ObjectExampleDTO(@JsonProperty("field1") String field1,
                                @JsonProperty("field2") Date field2,
                                @JsonProperty("field3") Date field3,
                                @JsonProperty("field4") boolean field4,
                                @JsonProperty("organizer") String organizer) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.organizer = organizer;
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

        @JsonIgnore
        @ICalEventChair
        public String organizer() {
            return organizer;
        }
    }

}