package com.edmunds.anonylead.configurator;

import com.edmunds.anonylead.factory.HBaseFactory;
import java.io.IOException;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:38 PM
 */
public abstract class SchemaConfigurator {
    @Autowired
    private HBaseFactory hBaseFactory;
    @Autowired
    private Configuration configuration;

    private String tableName;
    private Set<ColumnConfigurator> columns;

//    public abstract Set<ColumnConfigurator> getColumns();

    public void configureSchema() throws IOException {
        final HBaseAdmin hBaseAdmin = hBaseFactory.getHBaseAdmin(configuration);

        if(hBaseAdmin.tableExists(tableName)) {
            configureColumns(hBaseAdmin);
        } else {
            final HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            addColumns(tableDescriptor);
            hBaseAdmin.createTable(tableDescriptor);
            hBaseAdmin.enableTable(tableName);
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<ColumnConfigurator> getColumns() {
        return columns;
    }

    public void setColumns(Set<ColumnConfigurator> columns) {
        this.columns = columns;
    }

    private void configureColumns(HBaseAdmin hBaseAdmin) {
        // todo
    }

    private void addColumns(HTableDescriptor tableDescriptor) {
        // todo
    }
}
