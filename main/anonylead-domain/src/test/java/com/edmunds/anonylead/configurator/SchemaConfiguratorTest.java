package com.edmunds.anonylead.configurator;

import com.edmunds.anonylead.configurator.impl.TestSchemaConfigurator;
import com.edmunds.anonylead.factory.HBaseFactory;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static com.google.common.collect.Maps.newHashMap;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 8:11 PM
 */
@ContextConfiguration(locations = "/META-INF/spring/testSchema.xml")
public class SchemaConfiguratorTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private HBaseFactory hBaseFactory;

    @Autowired
    private Configuration configuration;

    @Test
    public void testConfigureTableNew() throws Exception {
        final SchemaConfigurator schemaConfigurator = (SchemaConfigurator) applicationContext.getBean("testSchema");

        schemaConfigurator.configureSchema();

        ArgumentCaptor<HTableDescriptor> tableCaptor = ArgumentCaptor.forClass(HTableDescriptor.class);

        verify(hBaseFactory.getHBaseAdmin(configuration)).createTable(tableCaptor.capture());
        verify(hBaseFactory.getHBaseAdmin(configuration)).enableTable(eq("testTable"));
    }
}
