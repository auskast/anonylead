package com.edmunds.anonylead.factory.mock.impl;

import org.apache.hadoop.conf.Configuration;
import org.mockito.Mock;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 7:47 PM
 */
@Component
public class ConfigurationMockFactory implements FactoryBean {
    @Mock Configuration configuration;

    private ConfigurationMockFactory() {
        initMocks(this);
    }

    @Override
    public Object getObject() throws Exception {
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
