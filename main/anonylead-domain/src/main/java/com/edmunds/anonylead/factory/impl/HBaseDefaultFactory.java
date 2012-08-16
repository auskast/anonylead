package com.edmunds.anonylead.factory.impl;

import com.edmunds.anonylead.factory.HBaseFactory;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:09 PM
 */
@Component
public class HBaseDefaultFactory implements HBaseFactory {
    @Override
    public HBaseAdmin getHBaseAdmin(Configuration configuration) throws ZooKeeperConnectionException,
        MasterNotRunningException {
        return new HBaseAdmin(configuration);
    }

    @Override
    public HTable getHTable(Configuration configuration, String tableName) throws IOException {
        return new HTable(configuration, tableName);
    }
}
