package io.dactilo.sumbawa.spring.ical.fiters.ical;

import io.dactilo.sumbawa.spring.ical.converter.SpringICalConfiguration;
import io.dactilo.sumbawa.spring.ical.converter.ical.DefaultICalendarStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@Import(SpringICalConfiguration.class)
public class ICalendarFormatterConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private SpringICalConfiguration springICalConfiguration;

    @Bean
    public ICalendarHandlerMethodReturnValueHandler csvHandlerMethodReturnValueHandler() {
        return new ICalendarHandlerMethodReturnValueHandler(defaultICalendarStreamer(), springICalConfiguration.objectToCalendarConverter());
    }

    @Bean
    public DefaultICalendarStreamer defaultICalendarStreamer() {
        return new DefaultICalendarStreamer();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(csvHandlerMethodReturnValueHandler());
    }
}
