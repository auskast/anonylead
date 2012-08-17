package com.edmunds.anonylead;

import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 7:49 PM
 */
public class RecordTest {
    @DataProvider(name = "recordProvider")
    public Object[][] generateRecord() {
        final List<Object[]> results = newArrayList();
        for(final Duration duration : Duration.values()) {
            for(final DigestPeriod digestPeriod : DigestPeriod.values()) {
                results.add(new Object[]{
                    "abc@def.com",
                    "firstname",
                    "lastname",
                    duration,
                    digestPeriod,
                    String.format("Record(FirstName: firstname, LastName: lastname, Email: abc@def.com, Duration: " +
                        "%s, DigestPeriod: %s)", duration, digestPeriod)
                });
            }
        }

        final Object[][] resultObject = new Object[results.size()][];
        for(int i = 0; i < results.size(); ++i) {
            resultObject[i] = results.get(i);
        }

        return resultObject;
    }

    @Test(dataProvider = "recordProvider")
    public void testRecord(String email, String firstName, String lastName, Duration duration,
                           DigestPeriod digestPeriod, String expected) {
        final Record record = new Record(email, firstName, lastName, duration, digestPeriod);
        assertEquals(record.toString(), expected);
        assertEquals(record.getEmail(), email);
        assertEquals(record.getFirstName(), firstName);
        assertEquals(record.getLastName(), lastName);
        assertEquals(record.getDuration(), duration);
        assertEquals(record.getDigestPeriod(), digestPeriod);
    }

    @Test
    public void testDefaultRecord() {
        final Record record = new Record();
        assertEquals(record.toString(), "Record(FirstName: null, LastName: null, Email: null, Duration: SHORT, " +
            "DigestPeriod: NONE)");
        assertNull(record.getEmail());
        assertNull(record.getFirstName());
        assertNull(record.getLastName());
        assertEquals(record.getDuration(), Duration.SHORT);
        assertEquals(record.getDigestPeriod(), DigestPeriod.NONE);
    }
}
