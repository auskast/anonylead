package com.edmunds.anonylead.factory.impl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:12 PM
 */
@Component
public class ConfigurationDefaultFactory implements FactoryBean {
    private static final String HBASE_CLUSTER_DIST = "hbase.cluster.distributed";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZK_PORT = "hbase.zookeeper.property.clientPort";
    private static final String HBASE_ZK_QUORUM = "hbase.zookeeper.quorum";

    @Override
    public Object getObject() throws Exception {
        final Configuration configuration = HBaseConfiguration.create();
        configuration.setBoolean(HBASE_CLUSTER_DIST, true);
        configuration.set(HBASE_ROOTDIR, "hdfs://dl1rhd302.internal.edmunds.com:8020/hbase");
        configuration.set(HBASE_ZK_PORT, "2181");
        configuration.set(HBASE_ZK_QUORUM,
            "dl1rhd310.internal.edmunds.com,dl1rhd311.internal.edmunds.com,dl1rhd312.internal.edmunds.com");

        return configuration;
    }

    @Override
    public Class getObjectType() {
        return Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
