package com.tr.ooxp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tr.ooxp.dao.HBaseDao;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void shouldAnswerWithTrue() throws IOException {

        // futzing about with mockito, trying to remember stuff
        HBaseDao dao = mock(HBaseDao.class);

        when(dao.list(any(), any(), any())).thenReturn(Collections.emptyList());

        List<String> aList = dao.list("asdasdas", "asdfsdf", null);

        assertTrue(CollectionUtils.isEmpty(aList));
    }

    @Test
    public void dateFormatTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        String dateString = "2019-12-17T17:46:33.702Z";
        ZonedDateTime zdt = ZonedDateTime.parse(dateString, formatter);

        assertNotNull(zdt);
        assertEquals(17, zdt.getHour());
        assertEquals(2019, zdt.getYear());
        assertEquals(12, zdt.getMonthValue());
        assertEquals(33, zdt.getSecond());
    }


    @Data
    @NoArgsConstructor
    static class SimplePojo {
        private ZonedDateTime someDate;
    }

    @Test
    public void testJacksonDateParsing() throws IOException {

        // testing the serialization, deserialization of dates by Jackson, given our format

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"));

        String dateString = "2018-04-05T14:31:17.254Z";
        String someJsonString = "{ \"someDate\" : \"" + dateString + "\" }";

        SimplePojo pojo = mapper.readValue(someJsonString, SimplePojo.class);

        assertNotNull(pojo);
        assertEquals(2018, pojo.getSomeDate().getYear());
        assertEquals(4, pojo.getSomeDate().getMonthValue());
        assertEquals(5,pojo.getSomeDate().getDayOfMonth());
        assertEquals(14,pojo.getSomeDate().getHour());
        assertEquals(31, pojo.getSomeDate().getMinute());

        String restoredJson = mapper.writeValueAsString(pojo);

        assertFalse(StringUtils.isEmpty(restoredJson));
        assertTrue(restoredJson.contains(dateString));

    }
}