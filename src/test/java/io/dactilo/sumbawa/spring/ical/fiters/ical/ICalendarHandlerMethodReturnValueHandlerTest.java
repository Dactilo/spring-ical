package io.dactilo.sumbawa.spring.ical.fiters.ical;

import io.dactilo.sumbawa.spring.ical.converter.api.AbstractICalendarStreamerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static io.dactilo.sumbawa.spring.ical.tests.ICSMatchers.isActuallyAnICSFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        SampleICSConfiguration.class
})
public class ICalendarHandlerMethodReturnValueHandlerTest {
    private MockMvc mockMvc;

    private static ServletContext servletContext() {
        return new MockServletContext("/", new FileSystemResourceLoader());
    }

    @Before
    public void setUp() {
        try (AnnotationConfigWebApplicationContext configurableWebApplicationContext = new AnnotationConfigWebApplicationContext()) {
            configurableWebApplicationContext.setServletContext(servletContext());
            configurableWebApplicationContext.register(SampleICSConfiguration.class);
            configurableWebApplicationContext.refresh();


            mockMvc = MockMvcBuilders.webAppContextSetup(configurableWebApplicationContext)
                    .build();
        }
    }

    @Test
    public void testSampleController_jsonOutputIsNotBroken() throws Exception {
        mockMvc.perform(get("/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"field1\":\"field 1\",\"field2\":1144274400000,\"field3\":1144274400000,\"field4\":false}]"));
    }

    @Test
    public void testSampleController_icsWorks() throws Exception {
        mockMvc.perform(get("/ics"))
                .andExpect(status().isOk())
                .andExpect(content().string(isActuallyAnICSFile()));
    }
}

@Configuration
@EnableWebMvc
@EnableICSFormatter
class SampleICSConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public SampleController sampleController() {
        return new SampleController();
    }
}

@Controller
@RequestMapping("/")
@ResponseStatus(HttpStatus.OK)
class SampleController {
    @RequestMapping(value = "json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<AbstractICalendarStreamerTest.ObjectExampleDTO>> json() throws ParseException {
        return new ResponseEntity<>(Collections.singletonList(
                new AbstractICalendarStreamerTest.ObjectExampleDTO("field 1", createEndDate(), createEndDate(), false, null)
        ), HttpStatus.OK);
    }

    @RequestMapping(value = "ics", method = RequestMethod.GET, produces = "text/calendar")
    public ResponseEntity<List<AbstractICalendarStreamerTest.ObjectExampleDTO>> calendar() throws ParseException {
        return new ResponseEntity<>(Collections.singletonList(
                new AbstractICalendarStreamerTest.ObjectExampleDTO("field 1", createStartDate(), createEndDate(), false, null)
        ), HttpStatus.OK);
    }

    private Date createStartDate() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse("2006-04-05");
    }

    private Date createEndDate() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse("2006-04-06");
    }
}