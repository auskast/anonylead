package com.edmunds.anonylead.dao;

import com.edmunds.anonylead.Record;
import com.edmunds.anonylead.exception.MaskedEmailNotFoundException;
import com.edmunds.anonylead.exception.MetaDataNotFoundException;
import com.edmunds.anonylead.exception.NoMaskedEmailHistoryException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 6:43 PM
 */
public interface AnonyLeadDao {
    public boolean putRecord(Record record) throws IOException;
    public String getTempEmail(String email) throws IOException, NoMaskedEmailHistoryException;
    public Record getRecord(String temp) throws IOException, MaskedEmailNotFoundException, MetaDataNotFoundException;
}
