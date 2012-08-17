package com.edmunds.anonylead.configurator.impl;

import com.edmunds.anonylead.configurator.ColumnConfigurator;
import com.edmunds.anonylead.configurator.SchemaConfigurator;
import java.util.Map;
import org.apache.hadoop.hbase.util.Bytes;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 6:10 PM
 */
public class TempEmailSchemaConfigurator extends SchemaConfigurator {
    public static final byte[] FAMILY_SHORT = Bytes.toBytes("short");
    public static final byte[] FAMILY_MEDIUM = Bytes.toBytes("medium");
    public static final byte[] FAMILY_LONG = Bytes.toBytes("long");

    public static final byte[] QUALIFIER_LEAD_EMAIL = Bytes.toBytes("email");

    private ColumnConfigurator shortColumnConfigurator;
    private ColumnConfigurator mediumColumnConfigurator;
    private ColumnConfigurator longColumnConfigurator;

    public ColumnConfigurator getShortColumnConfigurator() {
        return shortColumnConfigurator;
    }

    public void setShortColumnConfigurator(ColumnConfigurator shortColumnConfigurator) {
        this.shortColumnConfigurator = shortColumnConfigurator;
    }

    public ColumnConfigurator getMediumColumnConfigurator() {
        return mediumColumnConfigurator;
    }

    public void setMediumColumnConfigurator(ColumnConfigurator mediumColumnConfigurator) {
        this.mediumColumnConfigurator = mediumColumnConfigurator;
    }

    public ColumnConfigurator getLongColumnConfigurator() {
        return longColumnConfigurator;
    }

    public void setLongColumnConfigurator(ColumnConfigurator longColumnConfigurator) {
        this.longColumnConfigurator = longColumnConfigurator;
    }

    @Override
    protected Map<byte[], ColumnConfigurator> getColumns() {
        final Map<byte[], ColumnConfigurator> columns = newHashMap();

        columns.put(FAMILY_SHORT, shortColumnConfigurator);
        columns.put(FAMILY_MEDIUM, mediumColumnConfigurator);
        columns.put(FAMILY_LONG, longColumnConfigurator);

        return columns;
    }
}
