package io.dactilo.sumbawa.spring.ical.converter;

import io.dactilo.sumbawa.spring.ical.converter.api.ObjectToCalendarConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringICalConfiguration {
    @Bean
    public ObjectToCalendarConverter objectToCalendarConverter() {
        return new ObjectToCalendarConverter();
    }
}
