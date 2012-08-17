package com.edmunds.anonylead.configurator;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.io.hfile.Compression;
import org.apache.hadoop.hbase.regionserver.StoreFile;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:48 PM
 */
public class ColumnConfigurator {

    private static final boolean DEFAULT_BLOCKCACHE_ENABLED = HColumnDescriptor.DEFAULT_BLOCKCACHE;
    private static final int DEFAULT_BLOCK_SIZE = HColumnDescriptor.DEFAULT_BLOCKSIZE;
    private static final String DEFAULT_BLOOM_FILTER_TYPE = HColumnDescriptor.DEFAULT_BLOOMFILTER;
    private static final String DEFAULT_COMPRESSION_TYPE = Compression.Algorithm.NONE.name();
    private static final boolean DEFAULT_IN_MEMORY = HColumnDescriptor.DEFAULT_IN_MEMORY;
    private static final int DEFAULT_MAX_VERSIONS = HColumnDescriptor.DEFAULT_VERSIONS;
    private static final int DEFAULT_REPLICATION_SCOPE = HColumnDescriptor.DEFAULT_REPLICATION_SCOPE;
    private static final int DEFAULT_TIME_TO_LIVE = HColumnDescriptor.DEFAULT_TTL;

    private String name;
    private boolean blockCacheEnabled = DEFAULT_BLOCKCACHE_ENABLED;
    private int blockSize = DEFAULT_BLOCK_SIZE;
    private String bloomFilterType = DEFAULT_BLOOM_FILTER_TYPE;
    private String compressionType = DEFAULT_COMPRESSION_TYPE;
    private boolean inMemory = DEFAULT_IN_MEMORY;
    private int maxVersions = DEFAULT_MAX_VERSIONS;
    private int replicationScope = DEFAULT_REPLICATION_SCOPE;
    private int timeToLive = DEFAULT_TIME_TO_LIVE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlockCacheEnabled() {
        return blockCacheEnabled;
    }

    public void setBlockCacheEnabled(boolean blockCacheEnabled) {
        this.blockCacheEnabled = blockCacheEnabled;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public String getBloomFilterType() {
        return bloomFilterType;
    }

    public void setBloomFilterType(String bloomFilterType) {
        this.bloomFilterType = bloomFilterType;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public boolean isInMemory() {
        return inMemory;
    }

    public void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }

    public int getMaxVersions() {
        return maxVersions;
    }

    public void setMaxVersions(int maxVersions) {
        this.maxVersions = maxVersions;
    }

    public int getReplicationScope() {
        return replicationScope;
    }

    public void setReplicationScope(int replicationScope) {
        this.replicationScope = replicationScope;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public HColumnDescriptor getColumnDescriptor() {
        final HColumnDescriptor columnDescriptor = new HColumnDescriptor(name);

        columnDescriptor.setBlockCacheEnabled(blockCacheEnabled);
        columnDescriptor.setBlocksize(blockSize);
        columnDescriptor.setBloomFilterType(StoreFile.BloomType.valueOf(bloomFilterType));
        columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(compressionType));
        columnDescriptor.setInMemory(inMemory);
        columnDescriptor.setMaxVersions(maxVersions);
        columnDescriptor.setScope(replicationScope);
        columnDescriptor.setTimeToLive(timeToLive);

        return columnDescriptor;
    }
}
