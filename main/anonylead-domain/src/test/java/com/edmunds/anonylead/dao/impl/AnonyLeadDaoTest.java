package com.edmunds.anonylead.dao.impl;

import com.edmunds.anonylead.DigestPeriod;
import com.edmunds.anonylead.Duration;
import com.edmunds.anonylead.Record;
import com.edmunds.anonylead.dao.AnonyLeadDao;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.FAMILY_META_DATA;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.FAMILY_TEMP;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_DIGEST;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_FIRST_NAME;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_LAST_NAME;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_TEMP_EMAIL;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.QUALIFIER_LEAD_EMAIL;
import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 11:19 PM
 */
@ContextConfiguration(locations = "/META-INF/spring/testApplicationContext.xml")
public class AnonyLeadDaoTest extends AbstractTestNGSpringContextTests {
    @Mock Result leadEmailResult;
    @Mock Result tempEmailResult;
    @Mock KeyValue leadEmailKeyValue;
    @Mock KeyValue tempEmailKeyValue;

    private AnonyLeadDao anonyLeadDao;
    private HTable leadEmailTable;
    private HTable tempEmailTable;

    @BeforeMethod
    public void setup() throws Exception {
        initMocks(this);
        anonyLeadDao = (AnonyLeadDao) applicationContext.getBean("anonyLeadDao");
        leadEmailTable = ((AnonyLeadDaoImpl) anonyLeadDao).getLeadEmailTable();
        tempEmailTable = ((AnonyLeadDaoImpl) anonyLeadDao).getTempEmailTable();
        reset(leadEmailTable, tempEmailTable);
    }

    @DataProvider(name = "recordProvider")
    public Object[][] getRecords() {
        final List<Record> records = newArrayList();
        for(final Duration duration : Duration.values()) {
            for(final DigestPeriod digestPeriod : DigestPeriod.values()) {
                records.add(new Record("ABc@deF.com", "Patrick", "Fitzgerald", duration, digestPeriod));
            }
        }

        final Object[][] data = new Object[records.size()][];
        for(int i = 0; i < records.size(); ++i) {
            data[i] = new Object[]{ records.get(i) };
        }

        return data;
    }

    @Test(dataProvider = "recordProvider")
    public void testPutRecord(Record record) throws Exception {
        assertTrue(anonyLeadDao.putRecord(record));

        ArgumentCaptor<Put> leadEmailPutCaptor = ArgumentCaptor.forClass(Put.class);
        ArgumentCaptor<Put> tempEmailPutCaptor = ArgumentCaptor.forClass(Put.class);

        verify(leadEmailTable).put(leadEmailPutCaptor.capture());
        verify(tempEmailTable).put(tempEmailPutCaptor.capture());

        final Put leadEmailPut = leadEmailPutCaptor.getValue();
        final Put tempEmailPut = tempEmailPutCaptor.getValue();
        assertEquals(leadEmailPut.getRow(), Bytes.toBytes(StringUtils.lowerCase(record.getEmail())));
        assertEquals(getValue(leadEmailPut, FAMILY_META_DATA, QUALIFIER_FIRST_NAME),
            Bytes.toBytes(record.getFirstName()));
        assertEquals(getValue(leadEmailPut, FAMILY_META_DATA, QUALIFIER_LAST_NAME),
            Bytes.toBytes(record.getLastName()));
        assertEquals(getValue(leadEmailPut, FAMILY_META_DATA, QUALIFIER_DIGEST),
            Bytes.toBytes(record.getDigestPeriod().name()));

        assertEquals(getValue(tempEmailPut, record.getDuration().getFamily(), QUALIFIER_LEAD_EMAIL),
            Bytes.toBytes(StringUtils.lowerCase(record.getEmail())));

        assertEquals(getValue(leadEmailPut, FAMILY_TEMP, QUALIFIER_TEMP_EMAIL), tempEmailPut.getRow());
    }

    private byte[] getValue(Put put, byte[] family, byte[] qualifier) {
        return put.get(family, qualifier).iterator().next().getValue();
    }

    @Test(dataProvider = "recordProvider")
    public void testGetRecord(Record record) throws Exception {
        when(tempEmailTable.get(any(Get.class))).thenReturn(tempEmailResult);
        when(tempEmailResult.isEmpty()).thenReturn(false);
        when(tempEmailResult.list()).thenReturn(newArrayList(tempEmailKeyValue));
        when(tempEmailKeyValue.getValue()).thenReturn(Bytes.toBytes(record.getEmail()));
        when(tempEmailKeyValue.getFamily()).thenReturn(record.getDuration().getFamily());

        when(leadEmailTable.get(any(Get.class))).thenReturn(leadEmailResult);
        when(leadEmailResult.isEmpty()).thenReturn(false);
        final KeyValue firstName = mock(KeyValue.class);
        when(leadEmailResult.getColumnLatest(eq(FAMILY_META_DATA), eq(QUALIFIER_FIRST_NAME))).thenReturn(firstName);
        when(firstName.getValue()).thenReturn(Bytes.toBytes(record.getFirstName()));
        final KeyValue lastName = mock(KeyValue.class);
        when(leadEmailResult.getColumnLatest(eq(FAMILY_META_DATA), eq(QUALIFIER_LAST_NAME))).thenReturn(lastName);
        when(lastName.getValue()).thenReturn(Bytes.toBytes(record.getLastName()));
        final KeyValue digest = mock(KeyValue.class);
        when(leadEmailResult.getColumnLatest(eq(FAMILY_META_DATA), eq(QUALIFIER_DIGEST))).thenReturn(digest);
        when(digest.getValue()).thenReturn(Bytes.toBytes(record.getDigestPeriod().name()));

        assertEquals(anonyLeadDao.getRecord("kLg24GK4oS"), record);
    }

    @Test
    public void testGetTempEmail() throws Exception {
        when(leadEmailTable.get(any(Get.class))).thenReturn(leadEmailResult);
        when(leadEmailResult.isEmpty()).thenReturn(false);
        when(leadEmailResult.list()).thenReturn(newArrayList(leadEmailKeyValue));
        when(leadEmailKeyValue.getValue()).thenReturn(Bytes.toBytes("Aj09wAThk7"));

        assertEquals(anonyLeadDao.getTempEmail("ABc@deF.com"), "Aj09wAThk7");

        ArgumentCaptor<Get> getCaptor = ArgumentCaptor.forClass(Get.class);
        verify(leadEmailTable).get(getCaptor.capture());
        assertEquals(getCaptor.getValue().getRow(), Bytes.toBytes("abc@def.com"));
    }
}
