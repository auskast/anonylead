package com.edmunds.anonylead.factory.mock.impl;

import com.edmunds.anonylead.factory.HBaseFactory;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.mockito.Mock;
import org.springframework.stereotype.Component;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 7:45 PM
 */
@Component
public class HBaseMockFactory implements HBaseFactory {
    @Mock HBaseAdmin hBaseAdmin;

    private HBaseMockFactory() {
        initMocks(this);
    }

    @Override
    public HBaseAdmin getHBaseAdmin(Configuration configuration)
        throws ZooKeeperConnectionException, MasterNotRunningException {
        return hBaseAdmin;
    }

    @Override
    public HTable getHTable(Configuration configuration, String tableName) throws IOException {
        return mock(HTable.class);
    }
}
