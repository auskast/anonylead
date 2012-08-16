package com.edmunds.anonylead.factory;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:01 PM
 */
public interface HBaseFactory {
    public HBaseAdmin getHBaseAdmin(Configuration configuration) throws ZooKeeperConnectionException,
        MasterNotRunningException;

    public HTable getHTable(Configuration configuration, String tableName) throws IOException;
}
