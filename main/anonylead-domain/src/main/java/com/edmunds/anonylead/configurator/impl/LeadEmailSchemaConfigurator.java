package com.edmunds.anonylead.configurator.impl;

import com.edmunds.anonylead.configurator.ColumnConfigurator;
import com.edmunds.anonylead.configurator.SchemaConfigurator;
import java.util.Map;
import org.apache.hadoop.hbase.util.Bytes;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 4:42 PM
 */
public class LeadEmailSchemaConfigurator extends SchemaConfigurator {
    public static final byte[] FAMILY_META_DATA = Bytes.toBytes("metaData");
    public static final byte[] FAMILY_TEMP = Bytes.toBytes("temp");

    public static final byte[] QUALIFIER_FIRST_NAME = Bytes.toBytes("firstName");
    public static final byte[] QUALIFIER_LAST_NAME = Bytes.toBytes("lastName");
    public static final byte[] QUALIFIER_DIGEST = Bytes.toBytes("digest");
    public static final byte[] QUALIFIER_TEMP_EMAIL = Bytes.toBytes("email");

    private ColumnConfigurator metaDataColumnConfigurator;
    private ColumnConfigurator tempColumnConfigurator;

    public ColumnConfigurator getMetaDataColumnConfigurator() {
        return metaDataColumnConfigurator;
    }

    public void setMetaDataColumnConfigurator(ColumnConfigurator metaDataColumnConfigurator) {
        this.metaDataColumnConfigurator = metaDataColumnConfigurator;
    }

    public ColumnConfigurator getTempColumnConfigurator() {
        return tempColumnConfigurator;
    }

    public void setTempColumnConfigurator(ColumnConfigurator tempColumnConfigurator) {
        this.tempColumnConfigurator = tempColumnConfigurator;
    }

    @Override
    protected Map<byte[], ColumnConfigurator> getColumns() {
        final Map<byte[], ColumnConfigurator> columns = newHashMap();

        columns.put(FAMILY_META_DATA, metaDataColumnConfigurator);
        columns.put(FAMILY_TEMP, tempColumnConfigurator);

        return columns;
    }
}
