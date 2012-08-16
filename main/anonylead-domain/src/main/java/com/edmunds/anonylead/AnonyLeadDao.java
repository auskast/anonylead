package com.edmunds.anonylead;

import com.edmunds.anonylead.factory.HBaseFactory;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:27 PM
 */
@Component
public class AnonyLeadDao {
    private static final String TABLE_NAME = "anonyLead";

    private final HTable anonyLeadTable;

    @Autowired
    public AnonyLeadDao(HBaseFactory hBaseFactory, Configuration configuration) throws IOException {
        this.anonyLeadTable = hBaseFactory.getHTable(configuration, TABLE_NAME);
    }

    public void createRecord(String email) {
        final Put put = new Put();
    }
}
