package com.edmunds.anonylead;

import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.FAMILY_LONG;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.FAMILY_MEDIUM;
import static com.edmunds.anonylead.configurator.impl.TempEmailSchemaConfigurator.FAMILY_SHORT;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 5:19 PM
 */
public enum Duration {
    SHORT(FAMILY_SHORT),
    MEDIUM(FAMILY_MEDIUM),
    LONG(FAMILY_LONG);

    final byte[] family;

    private Duration(byte[] family){
        this.family = family;
    }

    public byte[] getFamily() {
        return family;
    }
}
