package com.edmunds.anonylead.configurator.impl;

import com.edmunds.anonylead.configurator.ColumnConfigurator;
import com.edmunds.anonylead.configurator.SchemaConfigurator;
import java.util.Map;
import org.apache.hadoop.hbase.util.Bytes;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 8:21 PM
 */
public class TestSchemaConfigurator extends SchemaConfigurator {
    public static final byte[] FAMILY_TEST = Bytes.toBytes("testFamily");

    private ColumnConfigurator testColumnConfigurator;

    public ColumnConfigurator getTestColumnConfigurator() {
        return testColumnConfigurator;
    }

    public void setTestColumnConfigurator(ColumnConfigurator testColumnConfigurator) {
        this.testColumnConfigurator = testColumnConfigurator;
    }

    @Override
    protected Map<byte[], ColumnConfigurator> getColumns() {
        final Map<byte[], ColumnConfigurator> columns = newHashMap();

        columns.put(FAMILY_TEST, testColumnConfigurator);

        return columns;
    }
}
