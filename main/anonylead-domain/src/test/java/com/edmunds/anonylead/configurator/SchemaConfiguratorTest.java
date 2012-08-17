package com.edmunds.anonylead.configurator;

import com.edmunds.anonylead.factory.HBaseFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.io.hfile.Compression;
import org.apache.hadoop.hbase.regionserver.StoreFile;
import org.apache.hadoop.hbase.util.Bytes;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 8:11 PM
 */
@ContextConfiguration(locations = "/META-INF/spring/testSchema.xml")
public class SchemaConfiguratorTest extends AbstractTestNGSpringContextTests {
    private static final String TABLE_NAME = "testTable";
    private static final String COLUMN_NAME = "testFamily";

    @Autowired
    private HBaseFactory hBaseFactory;

    @Autowired
    private Configuration configuration;

    @Mock HTableDescriptor tableDescriptor;

    private SchemaConfigurator schemaConfigurator;

    @BeforeMethod
    public void setup() throws Exception {
        initMocks(this);
        reset(hBaseFactory.getHBaseAdmin(configuration));
        schemaConfigurator = (SchemaConfigurator) applicationContext.getBean("testSchema");
        assertEquals(schemaConfigurator.getTableName(), TABLE_NAME);
    }

    @Test
    public void testConfigureTableNew() throws Exception {
        schemaConfigurator.configureSchema();

        ArgumentCaptor<HTableDescriptor> tableCaptor = ArgumentCaptor.forClass(HTableDescriptor.class);

        verify(hBaseFactory.getHBaseAdmin(configuration)).createTable(tableCaptor.capture());
        verify(hBaseFactory.getHBaseAdmin(configuration)).enableTable(eq(TABLE_NAME));
        verify(hBaseFactory.getHBaseAdmin(configuration), never()).disableTable(anyString());

        final HTableDescriptor tableDescriptor = tableCaptor.getValue();
        assertEquals(tableDescriptor.getNameAsString(), TABLE_NAME);
        assertEquals(tableDescriptor.getColumnFamilies().length, 1);

        verifyColumn(tableDescriptor.getFamily(Bytes.toBytes(COLUMN_NAME)));
    }

    @Test
    public void testConfigureTableNewColumn() throws Exception {
        when(hBaseFactory.getHBaseAdmin(configuration).tableExists(TABLE_NAME)).thenReturn(true);
        when(hBaseFactory.getHBaseAdmin(configuration).getTableDescriptor(Bytes.toBytes(TABLE_NAME))).thenReturn(
            new HTableDescriptor());

        schemaConfigurator.configureSchema();

        ArgumentCaptor<HColumnDescriptor> columnCaptor = ArgumentCaptor.forClass(HColumnDescriptor.class);

        verify(hBaseFactory.getHBaseAdmin(configuration)).disableTable(eq(TABLE_NAME));
        verify(hBaseFactory.getHBaseAdmin(configuration)).addColumn(eq(TABLE_NAME), columnCaptor.capture());
        verify(hBaseFactory.getHBaseAdmin(configuration)).enableTable(eq(TABLE_NAME));

        verifyColumn(columnCaptor.getValue());
    }

    @Test
    public void testConfigureTableModifyColumn() throws Exception {
        when(hBaseFactory.getHBaseAdmin(configuration).tableExists(TABLE_NAME)).thenReturn(true);
        when(hBaseFactory.getHBaseAdmin(configuration).getTableDescriptor(Bytes.toBytes(TABLE_NAME))).thenReturn(
            tableDescriptor);
        when(tableDescriptor.getFamily(Bytes.toBytes(COLUMN_NAME))).thenReturn(new HColumnDescriptor());

        schemaConfigurator.configureSchema();

        ArgumentCaptor<HColumnDescriptor> columnCaptor = ArgumentCaptor.forClass(HColumnDescriptor.class);

        verify(hBaseFactory.getHBaseAdmin(configuration)).disableTable(eq(TABLE_NAME));
        verify(hBaseFactory.getHBaseAdmin(configuration)).modifyColumn(eq(TABLE_NAME), columnCaptor.capture());
        verify(hBaseFactory.getHBaseAdmin(configuration)).enableTable(eq(TABLE_NAME));

        verifyColumn(columnCaptor.getValue());
    }

    private void verifyColumn(HColumnDescriptor columnDescriptor) {
        assertEquals(columnDescriptor.getNameAsString(), COLUMN_NAME);
        assertEquals(columnDescriptor.isBlockCacheEnabled(), false);
        assertEquals(columnDescriptor.getBlocksize(), 65536);
        assertEquals(columnDescriptor.getBloomFilterType(), StoreFile.BloomType.NONE);
        assertEquals(columnDescriptor.getCompressionType(), Compression.Algorithm.SNAPPY);
        assertEquals(columnDescriptor.isInMemory(), false);
        assertEquals(columnDescriptor.getMaxVersions(), 1);
        assertEquals(columnDescriptor.getScope(), 0);
        assertEquals(columnDescriptor.getTimeToLive(), Integer.MAX_VALUE);
    }
}
