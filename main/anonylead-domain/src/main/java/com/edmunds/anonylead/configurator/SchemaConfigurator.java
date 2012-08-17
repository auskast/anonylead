package com.edmunds.anonylead.configurator;

import com.edmunds.anonylead.factory.HBaseFactory;
import com.edmunds.anonylead.factory.impl.ConfigurationDefaultFactory;
import java.io.IOException;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:38 PM
 */
public abstract class SchemaConfigurator {
    @Autowired
    private HBaseFactory hBaseFactory;
    //@Autowired
    private Configuration configuration;

    private String tableName;

    public SchemaConfigurator() {
        try {
            this.configuration = (Configuration) new ConfigurationDefaultFactory().getObject();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract Map<byte[], ColumnConfigurator> getColumns();

    public HTable configureSchema() throws IOException {
        final HBaseAdmin hBaseAdmin = hBaseFactory.getHBaseAdmin(configuration);

        if(hBaseAdmin.tableExists(tableName)) {
            configureColumns(hBaseAdmin);
        } else {
            final HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            addColumns(tableDescriptor);
            hBaseAdmin.createTable(tableDescriptor);
            hBaseAdmin.enableTable(tableName);
        }

        return hBaseFactory.getHTable(configuration, tableName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private void configureColumns(HBaseAdmin hBaseAdmin) throws IOException {
        for(final byte[] columnName : getColumns().keySet()) {
            final ColumnConfigurator column = getColumns().get(columnName);
            column.setName(Bytes.toString(columnName));
            configureColumn(hBaseAdmin, column);
        }
    }

    private void configureColumn(HBaseAdmin hBaseAdmin, ColumnConfigurator column) throws IOException {
        final HColumnDescriptor columnDescriptor = column.getColumnDescriptor();
        if(hBaseAdmin.getTableDescriptor(Bytes.toBytes(tableName)).getFamily(columnDescriptor.getName()) == null) {
            hBaseAdmin.disableTable(tableName);
            hBaseAdmin.addColumn(tableName, columnDescriptor);
            hBaseAdmin.enableTable(tableName);
        } else {
            hBaseAdmin.disableTable(tableName);
            hBaseAdmin.modifyColumn(tableName, columnDescriptor);
            hBaseAdmin.enableTable(tableName);
        }
    }

    private void addColumns(HTableDescriptor tableDescriptor) {
        for(final byte[] columnName : getColumns().keySet()) {
            final ColumnConfigurator column = getColumns().get(columnName);
            column.setName(Bytes.toString(columnName));
            tableDescriptor.addFamily(column.getColumnDescriptor());
        }
    }
}
