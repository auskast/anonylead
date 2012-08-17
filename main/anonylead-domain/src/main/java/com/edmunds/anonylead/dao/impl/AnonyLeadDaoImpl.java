package com.edmunds.anonylead.dao.impl;

import com.edmunds.anonylead.DigestPeriod;
import com.edmunds.anonylead.Duration;
import com.edmunds.anonylead.Record;
import com.edmunds.anonylead.configurator.SchemaConfigurator;
import com.edmunds.anonylead.dao.AnonyLeadDao;
import com.edmunds.anonylead.exception.MaskedEmailNotFoundException;
import com.edmunds.anonylead.exception.MetaDataNotFoundException;
import com.edmunds.anonylead.exception.NoMaskedEmailHistoryException;
import java.io.IOException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.FAMILY_META_DATA;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.FAMILY_TEMP;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_DIGEST;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_FIRST_NAME;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_LAST_NAME;
import static com.edmunds.anonylead.configurator.impl.LeadEmailSchemaConfigurator.QUALIFIER_TEMP_EMAIL;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.FAMILY_LONG;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.FAMILY_MEDIUM;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.FAMILY_SHORT;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.QUALIFIER_LEAD_EMAIL;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 2:27 PM
 */
public class AnonyLeadDaoImpl implements AnonyLeadDao {
    private final HTable leadEmailTable;
    private final HTable tempEmailTable;

    @Autowired
    public AnonyLeadDaoImpl(SchemaConfigurator leadEmail, SchemaConfigurator tempEmail) throws IOException {
        this.leadEmailTable = leadEmail.configureSchema();
        this.tempEmailTable = tempEmail.configureSchema();
    }

    @Override
    public boolean putRecord(Record record) throws IOException {
        final byte[] tempEmail = createTempEmail();

        final Put tempEmailPut = new Put(tempEmail)
            .add(record.getDuration().getFamily(), QUALIFIER_LEAD_EMAIL,
                Bytes.toBytes(StringUtils.lowerCase(record.getEmail())));

        final Put leadEmailPut = new Put(Bytes.toBytes(StringUtils.lowerCase(record.getEmail())))
            .add(FAMILY_META_DATA, QUALIFIER_FIRST_NAME, Bytes.toBytes(record.getFirstName()))
            .add(FAMILY_META_DATA, QUALIFIER_LAST_NAME, Bytes.toBytes(record.getLastName()))
            .add(FAMILY_META_DATA, QUALIFIER_DIGEST, Bytes.toBytes(record.getDigestPeriod().toString()))
            .add(FAMILY_TEMP, QUALIFIER_TEMP_EMAIL, tempEmail);

        leadEmailTable.put(leadEmailPut);
        tempEmailTable.put(tempEmailPut);

        return true;
    }

    @Override
    public String getTempEmail(String email) throws IOException, NoMaskedEmailHistoryException {
        final Get get = new Get(Bytes.toBytes(StringUtils.lowerCase(email)))
            .addColumn(FAMILY_TEMP, QUALIFIER_TEMP_EMAIL);

        final Result result = leadEmailTable.get(get);

        if(isEmpty(result)) {
            throw new NoMaskedEmailHistoryException(email);
        }

        return Bytes.toString(result.list().iterator().next().getValue());
    }

    @Override
    public Record getRecord(String temp) throws IOException, MaskedEmailNotFoundException, MetaDataNotFoundException {
        final Result tempResult = tempEmailTable.get(
            new Get(Bytes.toBytes(temp))
                .addFamily(FAMILY_SHORT)
                .addFamily(FAMILY_MEDIUM)
                .addFamily(FAMILY_LONG)
        );

        if(isEmpty(tempResult)) {
            throw new MaskedEmailNotFoundException(temp);
        }

        final KeyValue keyValue = tempResult.list().iterator().next();
        final byte[] email = keyValue.getValue();
        final byte[] duration = keyValue.getFamily();

        final Result emailResult = leadEmailTable.get(
            new Get(email).addFamily(FAMILY_META_DATA)
        );

        if(isEmpty(emailResult)) {
            throw new MetaDataNotFoundException(Bytes.toString(email));
        }

        DigestPeriod digestPeriod;
        try {
            digestPeriod = DigestPeriod.valueOf(getColumnValue(emailResult, FAMILY_META_DATA, QUALIFIER_DIGEST));
        } catch(IllegalArgumentException e) {
            digestPeriod = DigestPeriod.NONE;
        }

        return new Record(
            Bytes.toString(email),
            getColumnValue(emailResult, FAMILY_META_DATA, QUALIFIER_FIRST_NAME),
            getColumnValue(emailResult, FAMILY_META_DATA, QUALIFIER_LAST_NAME),
            Duration.forFamily(duration),
            digestPeriod
        );
    }

    protected HTable getLeadEmailTable() {
        return leadEmailTable;
    }

    protected HTable getTempEmailTable() {
        return tempEmailTable;
    }

    private byte[] createTempEmail() throws IOException {
        final byte[] key = Bytes.toBytes(RandomStringUtils.randomAlphanumeric(10));
        final Result result = tempEmailTable.get(
            new Get(key)
                .addFamily(FAMILY_SHORT)
                .addFamily(FAMILY_MEDIUM)
                .addFamily(FAMILY_LONG)
        );
        if(isEmpty(result)) {
            return key;
        }
        return createTempEmail();
    }

    private String getColumnValue(Result result, byte[] family, byte[] qualifier) {
        final byte[] value = result.getColumnLatest(family, qualifier).getValue();
        return value == null ? "" : Bytes.toString(value);
    }

    private boolean isEmpty(Result result) {
        return result == null || result.isEmpty();
    }
}
